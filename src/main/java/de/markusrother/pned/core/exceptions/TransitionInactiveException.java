package de.markusrother.pned.core.exceptions;

public class TransitionInactiveException extends PetriNetException {

	public TransitionInactiveException(final String transitionId) {
		super("Transition is not active: " + transitionId);
	}

}
