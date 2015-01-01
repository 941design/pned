package de.markusrother.pned.gui.menus.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Superclass for actions that open a custom {@link javax.swing.JDialog} upon
 * performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractOpenDialogAction extends AbstractAction {

	/**
	 * The event target to which events are posted to.
	 */
	protected final GuiEventTarget eventTarget;

	/**
	 * <p>Constructor for AbstractOpenDialogAction.</p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 * @param label
	 *            a {@link java.lang.String} - this action's textual
	 *            representation.
	 * @param mnemonic
	 *            an int.
	 */
	protected AbstractOpenDialogAction(final GuiEventTarget eventTarget, final String label, final int mnemonic) {
		super(label);
		putValue(Action.MNEMONIC_KEY, mnemonic);
		this.eventTarget = eventTarget;
	}

}
