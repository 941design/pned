package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.menus.actions.EditSettingsAction;

public class PnedPreferencesMenu extends JMenu {

	private static final String label = "Preferences";

	PnedPreferencesMenu(final EventBus eventMulticaster) {
		super(label);
		setMnemonic(KeyEvent.VK_P);
		final Object eventSource = this;
		add(EditSettingsAction.newMenuItem(eventMulticaster, eventSource));
	}

}
