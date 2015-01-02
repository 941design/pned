package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.control.EventAwarePetriNet;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.menus.PnEditorMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;
import de.markusrother.pned.util.PetriNetEventLogger;

/**
 * <p>
 * PnEditorFrame class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnEditorFrame extends JFrame
	implements
		PetriNetListener,
		PetriNetIOListener {

	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(2000, 1000);

	private GuiEventBus eventBus;
	private final PnEditorMenuFactory menuFactory;
	private PnGridPanel grid;
	private PnedMenuBar pnedMenuBar;

	private File currentPath;

	/**
	 * <p>
	 * Constructor for PnEditorFrame.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} object.
	 */
	public PnEditorFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.eventBus = createNewContext();
		this.menuFactory = new PnEditorMenuFactory(eventBus);
		this.grid = new PnGridPanel(eventBus);
		this.pnedMenuBar = new PnedMenuBar(menuFactory);

		setPreferredSize(preferredSize);

		// final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// grid, new PnGridPanel(eventBus));
		// add(split, BorderLayout.CENTER);

		add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		setVisible(true);

		PetriNetEventLogger.log(eventBus);
	}

	/**
	 * <p>
	 * createNewContext.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	private GuiEventBus createNewContext() {
		final GuiEventBus eventBus = new GuiEventBus();
		createPetriNetModel(eventBus);
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		if (menuFactory != null) {
			menuFactory.setEventBus(eventBus);
		}
		eventBus.setCurrentDirectory(new PetriNetIOCommand(this, PetriNetIOCommand.Type.STAT, currentPath));
		return eventBus;
	}

	/**
	 * <p>
	 * createPetriNetModel.
	 * </p>
	 *
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
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
				final PnEditorFrame editorFrame = new PnEditorFrame("foobar");
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		// Assuming GC takes care of rest.
		// Can only be gc'ed if EventBus becomes garbage as well.
		getContentPane().remove(grid);
		eventBus.removeListener(PetriNetListener.class, this);
		eventBus.removeListener(PetriNetIOListener.class, this);

		this.eventBus = createNewContext();
		this.grid = new PnGridPanel(eventBus);
		this.pnedMenuBar = new PnedMenuBar(menuFactory);

		add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		// repaint(); // TODO ???
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
		createPetriNet(null);
		final File file = cmd.getFile();
		try {
			PNMLParser.parse(file, eventBus);
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
