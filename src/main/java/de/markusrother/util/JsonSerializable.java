package de.markusrother.util;

/**
 * <p>
 * Implementors of this interface can be serialized to JSON by a call to
 * {@link JsonSerializable#toJson()}, which returns a String.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface JsonSerializable {

	/**
	 * <p>
	 * Returns a JSON representation of this object.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the JSON String.
	 */
	String toJson();

}
