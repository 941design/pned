package de.markusrother.pned.core;

import de.markusrother.pned.core.model.TransitionModel;

/**
 * <p>
 * Exception for circumstances in which a
 * {@link de.markusrother.pned.core.model.TransitionModel} is thought to be
 * activated, wheras it isn't.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionInactiveException extends PetriNetException {

    /**
     * <p>
     * Constructor for TransitionInactiveException.
     * </p>
     *
     * @param transition
     *            a {@link de.markusrother.pned.core.model.TransitionModel} -
     *            the transition that cannot be executed.
     */
    public TransitionInactiveException(final TransitionModel transition) {
        super("Transition is not active: " + transition.getId());
    }

}
