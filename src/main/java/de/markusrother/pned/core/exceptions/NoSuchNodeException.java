package de.markusrother.pned.core.exceptions;

/**
 * <p>NoSuchNodeException class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NoSuchNodeException extends PetriNetException {

	/**
	 * <p>Constructor for NoSuchNodeException.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 */
	public NoSuchNodeException(final String nodeId) {
		super("No such node: " + nodeId);
	}

}
