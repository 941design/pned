package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.dialogs.EditSettingsDialog;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Action that opens an
 * {@link de.markusrother.pned.gui.components.dialogs.EditSettingsDialog} upon performing.
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
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection opens
	 * an {@link de.markusrother.pned.gui.components.dialogs.EditSettingsDialog}.
	 * </p>
	 *
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final PnCommandTarget commandTarget) {
		final Action action = new OpenEditSettingsDialogAction(commandTarget);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for EditSettingsAction.
	 * </p>
	 *
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 */
	public OpenEditSettingsDialogAction(final PnCommandTarget commandTarget) {
		super(commandTarget, label, mnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		EditSettingsDialog.open(commandTarget);
	}

}
