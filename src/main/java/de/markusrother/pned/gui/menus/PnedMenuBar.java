package de.markusrother.pned.gui.menus;

import javax.swing.JMenuBar;

public class PnedMenuBar extends JMenuBar {

	public PnedMenuBar(final EditMenuFactory editMenuFactory) {
		add(PnedFileMenu.INSTANCE);
		add(editMenuFactory.newEditMenu());
		add(PnedPreferencesMenu.INSTANCE);
	}

}
