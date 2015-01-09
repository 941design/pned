package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Abstract superclass for actions that may post events to a
 * {@link PnCommandTarget}.
 * </p>
 * 
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public abstract class AbstractStatelessAction extends AbstractAction {

	/** The listener to post events to. */
	protected final PnCommandTarget commandTarget;

	public AbstractStatelessAction(final PnCommandTarget commandTarget, final String name) {
		super(name);
		this.commandTarget = commandTarget;
	}

	/**
	 * <p>
	 * Constructor for AbstractOpenDialogAction.
	 * </p>
	 *
	 * @param name
	 *            a {@link java.lang.String} - this action's textual
	 *            representation.
	 * @param mnemonic
	 *            an int.
	 */
	public AbstractStatelessAction(final PnCommandTarget commandTarget, final String name, final int mnemonic) {
		this(commandTarget, name);
		putValue(Action.MNEMONIC_KEY, mnemonic);
	}

}
