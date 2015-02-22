package de.markusrother.pned.core;

/**
 * <p>
 * Exception for circumstances in which a
 * {@link de.markusrother.pned.core.model.NodeModel} is requested by an
 * identifier but is not contained in the
 * {@link de.markusrother.pned.core.model.PetriNetModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NoSuchNodeException extends PetriNetException {

    /**
     * <p>
     * Constructor for NoSuchNodeException.
     * </p>
     *
     * @param nodeId
     *            a {@link java.lang.String} - the identifier for which there
     *            was no node.
     */
    public NoSuchNodeException(final String nodeId) {
        super("No such node: " + nodeId);
    }

}
