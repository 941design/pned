package de.markusrother.pned.core.commands;

import java.util.EventObject;

/**
 * <p>
 * PlaceEditEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceEditCommand extends EventObject {

	public enum Type {
		SET_MARKING
	}

	private final String placeId;
	private final int marking;
	private final Type type;

	/**
	 * <p>
	 * Constructor for PlaceEditEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param placeId
	 *            a {@link java.lang.String} object.
	 * @param marking
	 *            a int.
	 */
	public PlaceEditCommand(final Object source, final Type type, final String placeId, final int marking) {
		super(source);
		this.type = type;
		this.placeId = placeId;
		this.marking = marking;
	}

	/**
	 * <p>
	 * Getter for the field <code>placeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>marking</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getMarking() {
		return marking;
	}

}
