package de.markusrother.pned.gui.menus;

import javax.swing.JMenuBar;

import de.markusrother.pned.gui.EventBus;

public class PnedMenuBar extends JMenuBar {

	public PnedMenuBar(final EventBus eventMulticaster, final EditMenuFactory editMenuFactory) {
		add(new PnedFileMenu(eventMulticaster));
		add(editMenuFactory.newEditMenu());
		add(new PnedPreferencesMenu(eventMulticaster));
	}

}
