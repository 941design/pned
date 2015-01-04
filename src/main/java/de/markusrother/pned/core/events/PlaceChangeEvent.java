package de.markusrother.pned.core.events;


/**
 * <p>PlaceChangeEvent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceChangeEvent extends PlaceEventObject {

	/**
	 * <p>Constructor for PlaceChangeEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a Type object.
	 * @param placeId a {@link java.lang.String} object.
	 * @param marking a int.
	 */
	public PlaceChangeEvent(final Object source, final Type type, final String placeId, final int marking) {
		super(source, type, placeId, marking);
	}

}
