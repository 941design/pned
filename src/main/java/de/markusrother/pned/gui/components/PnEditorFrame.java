package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.core.EventAwarePetriNet;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.menus.PnEditorMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;

/**
 * <p>PnEditorFrame class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnEditorFrame extends JFrame
	implements
		PetriNetListener {

	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(800, 600);

	private EventBus eventMulticaster;
	private final PnEditorMenuFactory menuFactory;
	private PnGridPanel grid;
	private PnedMenuBar pnedMenuBar;

	/**
	 * <p>Constructor for PnEditorFrame.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public PnEditorFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.menuFactory = new PnEditorMenuFactory();
		this.eventMulticaster = createNewContext();
		this.grid = new PnGridPanel(eventMulticaster);
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
			PNMLParser.parse(resource, eventMulticaster);
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
	 * <p>createNewContext.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	private EventBus createNewContext() {
		final EventBus eventMulticaster = new EventBus();
		createPetriNetModel(eventMulticaster);
		eventMulticaster.addListener(PetriNetListener.class, this);
		eventMulticaster.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		menuFactory.setEventMulticaster(eventMulticaster);
		return eventMulticaster;
	}

	/**
	 * <p>createPetriNetModel.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	private void createPetriNetModel(final EventBus eventMulticaster) {
		EventAwarePetriNet.create(eventMulticaster);
	}

	/**
	 * <p>main.</p>
	 *
	 * @param args a {@link java.lang.String} object.
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
	public void disposePetriNet(final PetriNetEditCommand cmd) {
		// Assuming GC takes care of rest.
		// Can only be gc'ed if EventBus becomes garbage as well.
		getContentPane().remove(grid);
		eventMulticaster.removeListener(PetriNetListener.class, this);

		this.eventMulticaster = createNewContext();
		this.grid = new PnGridPanel(eventMulticaster);
		this.pnedMenuBar = new PnedMenuBar(menuFactory);

		add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		// repaint(); // TODO ???
	}
}
