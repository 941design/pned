package de.markusrother.pned.core;

/**
 * <p>
 * Exception for circumstances in which a unique identifier is assigned to a
 * newly created node or edge, but is already used for an existing element!
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
	 *            a {@link java.lang.String} - the identifier that is no longer
	 *            available.
	 */
	public UnavailableIdException(final String elementId) {
		super("The id: " + elementId + " is not available");
	}

}
