package de.markusrother.pned.core;

public class NoSuchNodeException extends PetriNetInconsistencyException {

	public NoSuchNodeException(final String nodeId) {
		super("No such node: " + nodeId);
	}

}
