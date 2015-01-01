package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.GuiEventTarget;
import de.markusrother.pned.gui.actions.OpenEditSettingsDialogAction;

/**
 * <p>
 * PnedPreferencesMenu class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnedPreferencesMenu extends JMenu {

	/** Constant <code>label="Preferences"</code> */
	private static final String label = "Preferences";

	/**
	 * <p>
	 * Constructor for PnedPreferencesMenu.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	PnedPreferencesMenu(final GuiEventTarget eventTarget) {
		super(label);
		setMnemonic(KeyEvent.VK_P);
		add(OpenEditSettingsDialogAction.newMenuItem(eventTarget));
	}

}
