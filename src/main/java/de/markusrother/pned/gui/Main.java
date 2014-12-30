package de.markusrother.pned.gui;

import de.markusrother.pned.gui.components.PnEditorFrame;

/**
 * <p>Main class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Main {

	/** Constant <code>TITLE="Markus Rother (8544832)"</code> */
	private static final String TITLE = "Markus Rother (8544832)";

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
	public static void main(final String[] args) {
		new PnEditorFrame(TITLE);
	}
}
