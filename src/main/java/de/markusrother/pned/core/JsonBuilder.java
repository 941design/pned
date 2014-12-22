package de.markusrother.pned.core;

public class JsonBuilder {

	private final StringBuilder sb;

	JsonBuilder() {
		this.sb = new StringBuilder();
	}

	public JsonBuilder append(final String name, final int value) {
		return append(name, String.valueOf(value));
	}

	public JsonBuilder append(final String name, final String value) {
		startAttribute(name) //
				.append(value) //
				.appendComma();
		return this;
	}

	private JsonBuilder appendComma() {
		return append(',') //
				.append(' ');
	}

	private JsonBuilder startAttribute(final String name) {
		return append(name) //
				.append(':');
	}

	private JsonBuilder append(final String s) {
		sb.append(s);
		return this;
	}

	private JsonBuilder append(final char c) {
		sb.append(c);
		return this;
	}

	@Override
	public String toString() {
		sb.setLength(sb.length() - 2);
		return "{" + sb.toString() + "}";
	}

	public JsonBuilder appendList(final String name, final JsonBuildable... objects) {
		return startAttribute(name) //
				.append('[') //
				.appendObjects(objects) //
				.append(']') //
				.appendComma();
	}

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
