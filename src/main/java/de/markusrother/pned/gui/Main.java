package de.markusrother.pned.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.markusrother.pned.gui.components.PnFrame;

/**
 * <p>
 * Main class. Opens Petri net editor: {@link de.markusrother.pned.gui.components.PnFrame}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Main {

	/** Constant <code>TITLE="Markus Rother (8544832)"</code> */
	private static final String TITLE = "Markus Rother (8544832)";

	/**
	 * <p>
	 * main.
	 * </p>
	 *
	 * @param args
	 *            an array of {@link java.lang.String} objects.
	 */
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.exit(1);
		}

		final PnFrame frame = new PnFrame(TITLE);
		frame.pack();
		frame.setVisible(true);
	}

}
