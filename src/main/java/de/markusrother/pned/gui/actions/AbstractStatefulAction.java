package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.core.PnState;

/**
 * <p>
 * Abstract superclass for actions which may depend on a given {@link de.markusrother.pned.gui.core.PnState} -
 * the state of a {@link de.markusrother.pned.core.model.PetriNetModel} combined
 * with a {@link de.markusrother.pned.gui.components.PnFrame}. For example, a
 * certain action may only be available when certain conditions are met.
 * </p>
 * <p>
 * Usually, but not necessarily, subclasses post a command to the
 * {@link de.markusrother.pned.gui.control.PnEventBus} upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PnState
 * @see de.markusrother.pned.gui.control.PnEventBus
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
