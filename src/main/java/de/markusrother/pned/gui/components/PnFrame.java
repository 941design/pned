package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.control.EventAwarePetriNet;
import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOListener;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.actions.GuiState;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.menus.PnMenuFactory;
import de.markusrother.pned.gui.menus.PnMenuBar;
import de.markusrother.pned.io.PNMLParser;
import de.markusrother.pned.util.PetriNetGuiEventLogger;
import de.markusrother.swing.CustomScrollPaneUI;

/**
 * <p>
 * PnEditorFrame class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnFrame extends JFrame
	implements
		PetriNetListener,
		PetriNetIOListener {

	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(2000, 1000);
	/** Constant <code>gridSize</code> */
	private static final Dimension gridSize = new Dimension(2000, 2000);

	private PnMenuFactory menuFactory;
	private PnGridPanel grid;
	private PnMenuBar pnedMenuBar;

	private File currentPath;

	private GuiState state;
	private JScrollPane scrollPane;

	/**
	 * <p>
	 * Constructor for PnEditorFrame.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} object.
	 */
	public PnFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createNewContext();
		createComponents();

		setVisible(true);

		final GuiEventBus eventBus = state.getEventBus();
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		eventBus.setCurrentDirectory(new PetriNetIOCommand(this, PetriNetIOCommand.Type.CWD, currentPath));
	}

	/**
	 * <p>
	 * createAutoResizableScrollPane.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a {@link javax.swing.JScrollPane} object.
	 */
	private JScrollPane createAutoResizableScrollPane(final Component component) {
		final JScrollPane scrollPane = new JScrollPane(component, //
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		// Must set ui manually, because there is no entire LAF configured!
		// TODO - existing ui should rather be proxied!
		final CustomScrollPaneUI ui = new CustomScrollPaneUI();
		ui.addVerticalChangeListener(new VerticalComponentResizer(component));
		ui.addHorizontalChangeListener(new HorizontalComponentResizer(component));
		scrollPane.setUI(ui);
		return scrollPane;
	}

	/**
	 * <p>
	 * createNewContext.
	 * </p>
	 */
	private void createNewContext() {
		final GuiEventBus eventBus = new GuiEventBus();
		PetriNetGuiEventLogger.log(eventBus);

		this.state = new GuiState(eventBus);
		this.menuFactory = new PnMenuFactory(state);

		createPetriNetModel(eventBus);

		installListeners(eventBus);
	}

	/**
	 * <p>
	 * installListeners.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	private void installListeners(final GuiEventBus eventBus) {
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/**
	 * <p>
	 * createPetriNetModel.
	 * </p>
	 *
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	private void createPetriNetModel(final EventBus eventMulticaster) {
		EventAwarePetriNet.create(eventMulticaster);
	}

	/**
	 * <p>
	 * main.
	 * </p>
	 *
	 * @param args
	 *            a {@link java.lang.String} object.
	 */
	public static void main(final String... args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final @SuppressWarnings("unused") PnFrame editorFrame = new PnFrame("foobar");
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		disposeCurrentContext();
		removeComponents();
		createNewContext();
		createComponents();
		repaint();
	}

	/**
	 * <p>
	 * removeComponents.
	 * </p>
	 */
	private void removeComponents() {
		remove(grid);
		getContentPane().remove(grid);
	}

	/**
	 * <p>
	 * createComponents.
	 * </p>
	 */
	private void createComponents() {
		final GuiEventBus eventBus = state.getEventBus();
		final NodeFactoryImpl factory = new NodeFactoryImpl(state);
		this.grid = new PnGridPanel(eventBus, //
				menuFactory, //
				factory, //
				factory);
		this.grid.setPreferredSize(gridSize);
		this.pnedMenuBar = new PnMenuBar(menuFactory);
		this.scrollPane = createAutoResizableScrollPane(grid);

		add(scrollPane, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		setPreferredSize(preferredSize);
		pack();
	}

	/**
	 * <p>
	 * disposeCurrentContext.
	 * </p>
	 */
	private void disposeCurrentContext() {
		// Assuming GC takes care of rest.
		// Can only be gc'ed if EventBus becomes garbage as well.
		suspendListeners();
	}

	/**
	 * <p>
	 * suspendListeners.
	 * </p>
	 */
	private void suspendListeners() {
		final GuiEventBus eventBus = state.getEventBus();
		eventBus.removeListener(PetriNetListener.class, this);
		eventBus.removeListener(PetriNetIOListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		this.currentPath = cmd.getFile();
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		// FIXME - Creation and import should be handled by separate commands!
		// Or export should also be handled here, instead of EventAwarePetriNet
		// itself!
		createPetriNet(new PetriNetEditCommand(this, PetriNetEditCommand.Type.NEW));
		final File file = cmd.getFile();
		try {
			PNMLParser.parse(file, state.getEventBus());
		} catch (FileNotFoundException | XMLStreamException e) {
			// FIXME
			throw new RuntimeException("TODO");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		// IGNORE - exported by EventAwarePetriNet
	}

}
