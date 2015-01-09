package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.actions.OpenEditSettingsDialogAction;
import de.markusrother.pned.gui.commands.PnCommandTarget;

/**
 * <p>
 * PnedPreferencesMenu class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnPreferencesMenu extends JMenu {

	/** Constant <code>label="Preferences"</code> */
	private static final String label = "Preferences";

	/**
	 * <p>
	 * Constructor for PnedPreferencesMenu.
	 * </p>
	 *
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.commands.PnCommandTarget}
	 *            object.
	 */
	PnPreferencesMenu(final PnCommandTarget commandTarget) {
		super(label);
		setMnemonic(KeyEvent.VK_P);
		add(OpenEditSettingsDialogAction.newMenuItem(commandTarget));
	}

}
