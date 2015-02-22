package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

/**
 * <p>
 * Abstract superclass for actions which may depend on a given
 * {@link de.markusrother.pned.gui.control.PnState} - the state of a
 * {@link de.markusrother.pned.core.model.PetriNetModel} combined with a
 * {@link de.markusrother.pned.gui.components.PnFrame}. For example, a certain
 * action may only be available when certain conditions are met.
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
    protected final PnStateModel state;
    protected final PnCommandTarget commandTarget;

    /**
     * <p>
     * Constructor for AbstractGuiAction.
     * </p>
     *
     * @param name
     *            a {@link java.lang.String} - this action's name.
     * @param state
     *            a {@link de.markusrother.pned.gui.core.model.PnStateModel} -
     *            the current state.
     * @param commandTarget
     *            a
     *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
     *            object.
     */
    public AbstractStatefulAction(final PnStateModel state, final PnCommandTarget commandTarget, final String name) {
        super(name);
        this.state = state;
        this.commandTarget = commandTarget;
    }

    /**
     * <p>
     * Constructor for AbstractStatefulAction.
     * </p>
     *
     * @param state
     *            a {@link de.markusrother.pned.gui.core.model.PnStateModel}
     *            object.
     * @param commandTarget
     *            a
     *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
     *            object.
     * @param name
     *            a {@link java.lang.String} object.
     * @param mnemonic
     *            a int.
     */
    public AbstractStatefulAction(final PnStateModel state,
            final PnCommandTarget commandTarget,
            final String name,
            final int mnemonic) {
        this(state, commandTarget, name);
        putValue(Action.MNEMONIC_KEY, mnemonic);
    }

}
