package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.core.PnState;

/**
 * <p>
 * Abstract superclass for Actions that whose actions may depend on a given
 * {@link PnState}, representing the state of a
 * {@link de.markusrother.pned.core.model.PetriNetModel} together with a
 * {@link de.markusrother.pned.gui.components.PnFrame}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
abstract class AbstractStatefulAction extends AbstractAction {

	/** The current state. */
	protected final PnState state;

	/**
	 * <p>
	 * Constructor for AbstractGuiAction.
	 * </p>
	 *
	 * @param name
	 *            a {@link java.lang.String} - this action's name.
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} - the current
	 *            state.
	 */
	public AbstractStatefulAction(final String name, final PnState state) {
		super(name);
		this.state = state;
	}

	/**
	 * <p>
	 * Returns the {@link de.markusrother.pned.gui.control.PnEventBus}
	 * associated with the current state.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.PnEventBus} - the event
	 *         target to which events are posted to.
	 */
	protected PnEventBus getEventBus() {
		return state.getEventBus();
	}

}
