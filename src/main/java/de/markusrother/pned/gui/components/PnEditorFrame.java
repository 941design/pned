package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.gui.MockDataProvider;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.menus.EditMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;

public class PnEditorFrame extends JFrame {

	private static final Dimension preferredSize = new Dimension(800, 600);

	public PnEditorFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Container contentPane = getContentPane();

		final PnGridPanel grid = new PnGridPanel();

		final EditMenuFactory editMenuFactory = new EditMenuFactory(eventBus);
		final PnedMenuBar pnedMenuBar = new PnedMenuBar(editMenuFactory);

		setPreferredSize(preferredSize);
		setJMenuBar(pnedMenuBar);
		contentPane.add(grid, BorderLayout.CENTER);
		pack();
		setVisible(true);

		// TEST
		MockDataProvider.instantiate(eventBus);

		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));

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

	}

	public static void main(final String... args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final PnEditorFrame editorFrame = new PnEditorFrame("foobar");
			}
		});
	}
}
