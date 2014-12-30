package de.markusrother.util;

/**
 * <p>JsonBuilder class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class JsonBuilder {

	private final StringBuilder sb;

	/**
	 * <p>Constructor for JsonBuilder.</p>
	 */
	public JsonBuilder() {
		this.sb = new StringBuilder();
	}

	/**
	 * <p>append.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a int.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	public JsonBuilder append(final String name, final int value) {
		return append(name, String.valueOf(value));
	}

	/**
	 * <p>append.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	public JsonBuilder append(final String name, final String value) {
		startAttribute(name) //
				.append(value) //
				.appendComma();
		return this;
	}

	/**
	 * <p>appendComma.</p>
	 *
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	private JsonBuilder appendComma() {
		return append(',') //
				.append(' ');
	}

	/**
	 * <p>startAttribute.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	private JsonBuilder startAttribute(final String name) {
		return append(name) //
				.append(':');
	}

	/**
	 * <p>append.</p>
	 *
	 * @param s a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	private JsonBuilder append(final String s) {
		sb.append(s);
		return this;
	}

	/**
	 * <p>append.</p>
	 *
	 * @param c a char.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	private JsonBuilder append(final char c) {
		sb.append(c);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		sb.setLength(sb.length() - 2);
		return "{" + sb.toString() + "}";
	}

	/**
	 * <p>appendList.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param objects a {@link de.markusrother.util.JsonBuildable} object.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	public JsonBuilder appendList(final String name, final JsonBuildable... objects) {
		return startAttribute(name) //
				.append('[') //
				.appendObjects(objects) //
				.append(']') //
				.appendComma();
	}

	/**
	 * <p>appendObjects.</p>
	 *
	 * @param objects a {@link de.markusrother.util.JsonBuildable} object.
	 * @return a {@link de.markusrother.util.JsonBuilder} object.
	 */
	private JsonBuilder appendObjects(final JsonBuildable... objects) {
		if (objects.length == 0) {
			return this;
		}
		for (final JsonBuildable o : objects) {
			append(o.toJson()) //
					.appendComma();
		}
		sb.setLength(sb.length() - 2);
		return this;
	}
}
