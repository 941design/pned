package de.markusrother.pned.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

public class PnedPreferencesMenu extends JMenu {

	public static final JMenu INSTANCE = new PnedPreferencesMenu();

	private static final String PREFERENCES = "Preferences";

	private PnedPreferencesMenu() {
		super(PREFERENCES);
		setMnemonic(KeyEvent.VK_P);
	}
}
