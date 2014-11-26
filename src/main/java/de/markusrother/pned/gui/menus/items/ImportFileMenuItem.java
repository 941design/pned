package de.markusrother.pned.gui.menus.items;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

public class ImportFileMenuItem extends JMenuItem {

	public static final JMenuItem INSTANCE = new ImportFileMenuItem();

	private static final String IMPORT = "Import";

	private ImportFileMenuItem() {
		super(IMPORT);
		setMnemonic(KeyEvent.VK_I);
	}
}
