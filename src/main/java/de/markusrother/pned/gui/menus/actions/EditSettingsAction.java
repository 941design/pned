package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.components.settings.EditSettingsDialog;

/**
 * <p>EditSettingsAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EditSettingsAction extends AbstractAction {

	/** Constant <code>label="Settings"</code> */
	private static final String label = "Settings";
	/** Constant <code>mnemonic=KeyEvent.VK_S</code> */
	private static final int mnemonic = KeyEvent.VK_S;

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param eventSource a {@link java.lang.Object} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final EventBus eventMulticaster, final Object eventSource) {
		final EditSettingsAction action = new EditSettingsAction(eventMulticaster, eventSource);
		return new JMenuItem(action);
	}

	private final Object source;
	private final EventBus eventMulticaster;

	/**
	 * <p>Constructor for EditSettingsAction.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 */
	public EditSettingsAction(final EventBus eventMulticaster, final Object source) {
		super(label);
		this.eventMulticaster = eventMulticaster;
		this.source = source;
		putValue(Action.MNEMONIC_KEY, mnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		EditSettingsDialog.open(eventMulticaster);
	}

}
