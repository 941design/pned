package de.markusrother.pned.gui.menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenuBar;

public class PnedMenuBar extends JMenuBar
	implements
		PropertyChangeListener {

	private final PnedEditMenu pnedEditMenu;

	public PnedMenuBar(final EditMenuFactory editMenuFactory) {
		this.pnedEditMenu = editMenuFactory.newEditMenu();

		add(PnedFileMenu.INSTANCE);
		add(pnedEditMenu);
		add(PnedPreferencesMenu.INSTANCE);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		pnedEditMenu.propertyChange(evt);
	}
}
