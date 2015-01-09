package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.commands.PnCommandTarget;

/**
 * <p>
 * Superclass for actions that open a custom {@link javax.swing.JDialog} upon
 * performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
abstract class AbstractOpenDialogAction extends AbstractAction {

	/**
	 * The event target to which events are posted to.
	 */
	protected final PnCommandTarget commandTarget;

	/**
	 * <p>
	 * Constructor for AbstractOpenDialogAction.
	 * </p>
	 *
	 * @param commandTarget
	 *            an {@link de.markusrother.pned.gui.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param label
	 *            a {@link java.lang.String} - this action's textual
	 *            representation.
	 * @param mnemonic
	 *            an int.
	 */
	protected AbstractOpenDialogAction(final PnCommandTarget commandTarget, final String label, final int mnemonic) {
		super(label);
		putValue(Action.MNEMONIC_KEY, mnemonic);
		this.commandTarget = commandTarget;
	}

}
