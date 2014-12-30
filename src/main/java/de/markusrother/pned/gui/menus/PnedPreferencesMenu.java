package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.menus.actions.EditSettingsAction;

/**
 * <p>PnedPreferencesMenu class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnedPreferencesMenu extends JMenu {

	/** Constant <code>label="Preferences"</code> */
	private static final String label = "Preferences";

	/**
	 * <p>Constructor for PnedPreferencesMenu.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	PnedPreferencesMenu(final EventBus eventMulticaster) {
		super(label);
		setMnemonic(KeyEvent.VK_P);
		final Object eventSource = this;
		add(EditSettingsAction.newMenuItem(eventMulticaster, eventSource));
	}

}
