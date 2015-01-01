package de.markusrother.pned.core.exceptions;

/**
 * <p>UnavailableIdException class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class UnavailableIdException extends Exception {

	/**
	 * <p>Constructor for UnavailableIdException.</p>
	 *
	 * @param elementId a {@link java.lang.String} object.
	 */
	public UnavailableIdException(final String elementId) {
		super("The id: " + elementId + " is not available");
	}

}
