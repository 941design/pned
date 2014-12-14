package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.menus.actions.EditSettingsAction;

public class PnedPreferencesMenu extends JMenu {

	public static final JMenu INSTANCE = new PnedPreferencesMenu();

	private static final String label = "Preferences";

	private PnedPreferencesMenu() {
		super(label);
		setMnemonic(KeyEvent.VK_P);
		final Object eventSource = this;
		add(EditSettingsAction.newMenuItem(eventSource));
	}

}
