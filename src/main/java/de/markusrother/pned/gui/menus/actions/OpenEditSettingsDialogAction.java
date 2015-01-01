package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.GuiEventTarget;
import de.markusrother.pned.gui.dialogs.EditSettingsDialog;

/**
 * <p>
 * Action that opens an {@link EditSettingsDialog} upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenEditSettingsDialogAction extends AbstractOpenDialogAction {

	/** Constant <code>label="Settings"</code> */
	private static final String label = "Settings";
	/** Constant <code>mnemonic=KeyEvent.VK_S</code> */
	private static final int mnemonic = KeyEvent.VK_S;

	/**
	 * <p>
	 * Creates and returns a {@link JMenuItem} where selection opens an
	 * {@link EditSettingsDialog}.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} to be posted
	 *            to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final GuiEventTarget eventTarget) {
		final Action action = new OpenEditSettingsDialogAction(eventTarget);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for EditSettingsAction.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} to be posted
	 *            to.
	 */
	public OpenEditSettingsDialogAction(final GuiEventTarget eventTarget) {
		super(eventTarget, label, mnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		EditSettingsDialog.open(eventTarget);
	}

}
