package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.markusrother.pned.gui.MockDataProvider;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.menus.EditMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;

public class PnEditorFrame extends JFrame {

	private static final String TITLE = "Petri Net Easy";
	private static final Dimension preferredSize = new Dimension(800, 600);

	public PnEditorFrame() {
		super(TITLE);
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
	}

	public static void main(final String... args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final PnEditorFrame editorFrame = new PnEditorFrame();
			}
		});
	}
}
