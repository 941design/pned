package de.markusrother.util;

import java.util.regex.Pattern;

/**
 * <p>Abstract Patterns class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class Patterns {

	// TODO - Limit to Integer.MAX_VALUE
	/** Constant <code>intPattern</code> */
	public static final Pattern intPattern = Pattern.compile("0|([1-9][0-9]*)");

	/**
	 * <p>Constructor for Patterns.</p>
	 */
	private Patterns() {
	}

}
