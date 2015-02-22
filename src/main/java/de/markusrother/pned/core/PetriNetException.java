package de.markusrother.pned.core;

/**
 * <p>
 * Abstract superclass for exceptional circumstances that occur in operations on
 * {@link de.markusrother.pned.core.model.PetriNetModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PetriNetException extends Exception {

    /**
     * <p>
     * Constructor for PetriNetInconsistencyException.
     * </p>
     *
     * @param message
     *            a {@link java.lang.String} - the detail message.
     */
    public PetriNetException(final String message) {
        super(message);
    }

}
