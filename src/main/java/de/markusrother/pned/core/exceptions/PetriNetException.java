package de.markusrother.pned.core.exceptions;

/**
 * <p>
 * PetriNetInconsistencyException class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetException extends Exception {

	/**
	 * <p>
	 * Constructor for PetriNetInconsistencyException.
	 * </p>
	 *
	 * @param message
	 *            a {@link java.lang.String} object.
	 */
	public PetriNetException(final String message) {
		super(message);
	}

}
