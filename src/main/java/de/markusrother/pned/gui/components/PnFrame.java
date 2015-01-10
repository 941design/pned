package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.control.EventAwarePetriNet;
import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOListener;
import de.markusrother.pned.gui.components.menus.PnMenuBar;
import de.markusrother.pned.gui.components.menus.PnMenuFactory;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.control.commands.PetriNetListener;
import de.markusrother.pned.gui.control.commands.SetNodeTypeCommand;
import de.markusrother.pned.io.PNMLParser;
import de.markusrother.pned.util.PnEventLogger;

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

	private PnMenuBar pnedMenuBar;
	private JScrollPane scrollPane;
	private PnGridPanel grid;
	private File currentPath;
	private PnState state;
	private PnMenuFactory menuFactory;

	/**
	 * <p>
	 * Constructor for PnEditorFrame.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} object.
	 */
	public PnFrame(final String title, final Dimension preferredSize) {
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(preferredSize);

		createContext();
		createComponents();
	}

	/**
	 * <p>
	 * createNewContext.
	 * </p>
	 */
	private void createContext() {
		final PnEventBus eventBus = new PnEventBus();
		PnEventLogger.log(eventBus);

		this.state = new PnState(eventBus);
		this.menuFactory = new PnMenuFactory(state);

		createPetriNetModel(eventBus);
		installListeners(eventBus);

		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, //
				PnState.NodeCreationMode.PLACE));
		eventBus.setCurrentDirectory(new PetriNetIOCommand(this, //
				PetriNetIOCommand.Type.CWD, //
				currentPath));
	}

	/**
	 * <p>
	 * installListeners.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.PnEventBus} object.
	 */
	private void installListeners(final PnEventBus eventBus) {
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/**
	 * <p>
	 * createPetriNetModel.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	private void createPetriNetModel(final EventBus eventBus) {
		EventAwarePetriNet.create(eventBus);
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		removeContext();
		removeComponents();
		createContext();
		createComponents();
		pack();
		revalidate();
		repaint();
	}

	/**
	 * <p>
	 * removeComponents.
	 * </p>
	 */
	private void removeComponents() {
		remove(scrollPane);
	}

	/**
	 * <p>
	 * createComponents.
	 * </p>
	 */
	private void createComponents() {

		final PnEventBus eventBus = state.getEventBus();

		final ComponentFactory factory = new ComponentFactory(state);

		this.grid = new PnGridPanel(eventBus, menuFactory, factory, factory);
		this.pnedMenuBar = new PnMenuBar(menuFactory);
		this.scrollPane = new JScrollPane(grid);
		// this.scrollPane.setColumnHeaderView(...);
		// this.scrollPane.setRowHeaderView(...);
		add(scrollPane, BorderLayout.CENTER);

		setJMenuBar(pnedMenuBar);
	}

	/**
	 * <p>
	 * disposeCurrentContext.
	 * </p>
	 */
	private void removeContext() {
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
		final PnEventBus eventBus = state.getEventBus();
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
