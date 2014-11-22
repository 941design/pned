package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class PnEditorFrame extends JFrame {

	private static final String TITLE = "Petri Net Easy";
	private static final Dimension preferredSize = new Dimension(800, 600);

	public PnEditorFrame() {
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Container contentPane = getContentPane();
		final PnGridPanel grid = new PnGridPanel();
		setPreferredSize(preferredSize);
		// add(grid); // TODO ??
		contentPane.add(grid, BorderLayout.CENTER);
		pack();
		setVisible(true);
		// TEST
		final MockDataProvider mock = new MockDataProvider();
		eventBus.addNodeListener(mock);
		eventBus.addNodeSelectionListener(mock);
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
