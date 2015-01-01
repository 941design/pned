package de.markusrother.pned.core.exceptions;

public class UnavailableIdException extends Exception {

	public UnavailableIdException(final String elementId) {
		super("The id: " + elementId + " is not available");
	}

}
