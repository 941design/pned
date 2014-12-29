package de.markusrother.pned.gui.menus;

import javax.swing.JMenuBar;

public class PnedMenuBar extends JMenuBar {

	public PnedMenuBar(final PnEditorMenuFactory menuFactory) {
		add(menuFactory.newFileMenu());
		add(menuFactory.newEditMenu());
		add(menuFactory.newPreferencesMenu());
	}

}
