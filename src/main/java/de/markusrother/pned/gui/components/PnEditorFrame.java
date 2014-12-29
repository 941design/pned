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
import de.markusrother.pned.gui.menus.EditMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;

public class PnEditorFrame extends JFrame
	implements
		PetriNetListener {

	private static final Dimension preferredSize = new Dimension(800, 600);

	private PnGridPanel grid;
	private EventBus eventMulticaster;
	private final EditMenuFactory editMenuFactory;

	public PnEditorFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.editMenuFactory = new EditMenuFactory();

		final PnGridPanel grid = createNewContext();
		getContentPane().add(grid, BorderLayout.CENTER);

		final PnedMenuBar pnedMenuBar = new PnedMenuBar(eventMulticaster, editMenuFactory);

		setPreferredSize(preferredSize);
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

	private PnGridPanel createNewContext() {
		eventMulticaster = new EventBus();
		grid = new PnGridPanel(eventMulticaster);
		EventAwarePetriNet.create(eventMulticaster);
		eventMulticaster.addListener(PetriNetListener.class, this);
		eventMulticaster.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		editMenuFactory.setEventMulticaster(eventMulticaster);
		return grid;
	}

	public static void main(final String... args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final PnEditorFrame editorFrame = new PnEditorFrame("foobar");
			}
		});
	}

	@Override
	public void disposePetriNet(final PetriNetEditCommand cmd) {
		// Assuming GC takes care of rest.
		// Can only be gc'ed if EventBus becomes garbage as well.
		getContentPane().remove(grid);
		eventMulticaster.removeListener(PetriNetListener.class, this);
		final PnGridPanel grid = createNewContext();
		getContentPane().add(grid, BorderLayout.CENTER);
		pack();
		// repaint(); // TODO ???
	}
}
