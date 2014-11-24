package de.markusrother.pned.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

public class PnedFileMenu extends JMenu {

	public static final JMenu INSTANCE = new PnedFileMenu();

	private static final String FILE = "File";

	private PnedFileMenu() {
		super(FILE);
		setMnemonic(KeyEvent.VK_F);
		add(NewFileMenuItem.INSTANCE);
		add(ImportFileMenuItem.INSTANCE);
		add(ExportFileMenuItem.INSTANCE);
	}
}
