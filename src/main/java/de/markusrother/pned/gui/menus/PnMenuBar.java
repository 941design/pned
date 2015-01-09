package de.markusrother.pned.gui.menus;

import javax.swing.JMenuBar;

/**
 * <p>PnedMenuBar class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnMenuBar extends JMenuBar {

	/**
	 * <p>Constructor for PnedMenuBar.</p>
	 *
	 * @param menuFactory a {@link de.markusrother.pned.gui.menus.PnMenuFactory} object.
	 */
	public PnMenuBar(final PnMenuFactory menuFactory) {
		add(menuFactory.newFileMenu());
		add(menuFactory.newEditMenu());
		add(menuFactory.newPreferencesMenu());
	}

}
