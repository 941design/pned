package de.markusrother.pned.gui.menus;

import javax.swing.JMenuBar;

/**
 * <p>PnedMenuBar class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnedMenuBar extends JMenuBar {

	/**
	 * <p>Constructor for PnedMenuBar.</p>
	 *
	 * @param menuFactory a {@link de.markusrother.pned.gui.menus.PnEditorMenuFactory} object.
	 */
	public PnedMenuBar(final PnEditorMenuFactory menuFactory) {
		add(menuFactory.newFileMenu());
		add(menuFactory.newEditMenu());
		add(menuFactory.newPreferencesMenu());
	}

}
