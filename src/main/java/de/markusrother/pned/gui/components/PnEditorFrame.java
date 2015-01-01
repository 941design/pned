package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.core.EventAwarePetriNet;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.menus.PnEditorMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;

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
	private static final Dimension preferredSize = new Dimension(800, 600);

	private EventBus eventBus;
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
		add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		setVisible(true);

		// TODO - use file dialog!
		final String path = "/examples/Beispiel1.pnml";
		final URL resource = PNMLParser.class.getResource(path);
		try {
			PNMLParser.parse(resource, eventBus);
		} catch (final XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// PetriNetEventLogger.instantiate(eventMulticaster);
	}

	/**
	 * <p>
	 * createNewContext.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	private EventBus createNewContext() {
		final EventBus eventBus = new EventBus();
		createPetriNetModel(eventBus);
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		if (menuFactory != null) {
			menuFactory.setEventBus(eventBus);
		}
		eventBus.setCurrentDirectory(new PetriNetIOCommand(this, currentPath));
		return eventBus;
	}

	/**
	 * <p>
	 * createPetriNetModel.
	 * </p>
	 *
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.gui.EventBus} object.
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
		// IGNORE
	}
}
