package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.menus.items.ExportFileMenuItem;
import de.markusrother.pned.gui.menus.items.ImportFileMenuItem;
import de.markusrother.pned.gui.menus.items.NewFileMenuItem;

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
