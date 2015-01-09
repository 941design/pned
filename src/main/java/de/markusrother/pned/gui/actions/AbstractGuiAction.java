package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.core.PnState;

/**
 * <p>
 * AbstractGuiAction class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractGuiAction extends AbstractAction {

	protected final Object source;
	protected final PnState state;

	/**
	 * <p>
	 * Constructor for AbstractGuiAction.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} object.
	 */
	public AbstractGuiAction(final String label, final Object source, final PnState state) {
		super(label);
		this.source = source;
		this.state = state;
	}

	/**
	 * <p>
	 * getEventBus.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.PnEventBus} object.
	 */
	protected PnEventBus getEventBus() {
		return state.getEventBus();
	}

}
