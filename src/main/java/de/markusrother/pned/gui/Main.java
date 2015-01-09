package de.markusrother.pned.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.markusrother.pned.gui.components.PnFrame;

/**
 * <p>
 * Main class.
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
			/**
			 * 
			 * http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
			 * html TODO
			 * 
			 * You can specify the L&F at the command line by using the -D flag
			 * to set the swing.defaultlaf property. For example:
			 * 
			 * java
			 * -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel
			 * MyApp
			 */
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO - call show() from here
		final @SuppressWarnings("unused") PnFrame pnEditorFrame = new PnFrame(TITLE);
	}
}
