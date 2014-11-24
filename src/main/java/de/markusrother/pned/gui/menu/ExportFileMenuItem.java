package de.markusrother.pned.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

public class ExportFileMenuItem extends JMenuItem {

	public static final JMenuItem INSTANCE = new ExportFileMenuItem();

	private static final String EXPORT = "Export";

	private ExportFileMenuItem() {
		super(EXPORT);
		setMnemonic(KeyEvent.VK_E);
	}
}
