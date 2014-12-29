package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.menus.actions.NewNetAction;
import de.markusrother.pned.gui.menus.actions.OpenExportDialogAction;
import de.markusrother.pned.gui.menus.actions.OpenImportDialogAction;

public class PnedFileMenu extends JMenu {

	private static final String label = "File";

	PnedFileMenu(final EventBus eventMulticaster) {
		super(label);
		setMnemonic(KeyEvent.VK_F);
		add(NewNetAction.newMenuItem(eventMulticaster));
		add(OpenImportDialogAction.newMenuItem(eventMulticaster));
		add(OpenExportDialogAction.newMenuItem(eventMulticaster));
	}

}
