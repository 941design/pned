package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.menus.actions.OpenExportDialogAction;
import de.markusrother.pned.gui.menus.actions.OpenImportDialogAction;
import de.markusrother.pned.gui.menus.items.NewNetMenuItem;

public class PnedFileMenu extends JMenu {

	public static final JMenu INSTANCE = new PnedFileMenu();

	private static final String label = "File";

	private PnedFileMenu() {
		super(label);
		setMnemonic(KeyEvent.VK_F);
		add(NewNetMenuItem.INSTANCE);
		add(OpenImportDialogAction.newMenuItem());
		add(OpenExportDialogAction.newMenuItem());
	}

}
