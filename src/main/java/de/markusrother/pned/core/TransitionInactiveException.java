package de.markusrother.pned.core;


/**
 * <p>TransitionInactiveException class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionInactiveException extends PetriNetException {

	/**
	 * <p>Constructor for TransitionInactiveException.</p>
	 *
	 * @param transitionId a {@link java.lang.String} object.
	 */
	public TransitionInactiveException(final String transitionId) {
		super("Transition is not active: " + transitionId);
	}

}
