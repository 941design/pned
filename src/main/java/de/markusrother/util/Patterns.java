package de.markusrother.util;

import java.util.regex.Pattern;

public abstract class Patterns {

	// TODO - Limit to Integer.MAX_VALUE
	public static final Pattern intPattern = Pattern.compile("0|([1-9][0-9]*)");

	private Patterns() {
	}

}
