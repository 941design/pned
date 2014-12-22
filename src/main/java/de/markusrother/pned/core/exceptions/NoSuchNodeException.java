package de.markusrother.pned.core.exceptions;

public class NoSuchNodeException extends PetriNetInconsistencyException {

	public NoSuchNodeException(final String nodeId) {
		super("No such node: " + nodeId);
	}

}
