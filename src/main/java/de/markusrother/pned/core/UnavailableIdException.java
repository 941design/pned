package de.markusrother.pned.core;

/**
 * <p>
 * UnavailableIdException class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class UnavailableIdException extends PetriNetException {

	/**
	 * <p>
	 * Constructor for UnavailableIdException.
	 * </p>
	 *
	 * @param elementId
	 *            a {@link java.lang.String} object.
	 */
	public UnavailableIdException(final String elementId) {
		super("The id: " + elementId + " is not available");
	}

}
