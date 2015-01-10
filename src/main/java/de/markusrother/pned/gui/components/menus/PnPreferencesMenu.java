package de.markusrother.pned.gui.components.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.actions.OpenEditSettingsDialogAction;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Preferences menu.
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
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            object.
	 */
	PnPreferencesMenu(final PnState state, final PnCommandTarget commandTarget) {
		super(label);

		setMnemonic(KeyEvent.VK_P);

		add(OpenEditSettingsDialogAction.newMenuItem(state, commandTarget));
	}

}
