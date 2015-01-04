package de.markusrother.pned.core.commands;

import de.markusrother.pned.core.events.PlaceEventObject;

/**
 * <p>PlaceEditCommand class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceEditCommand extends PlaceEventObject {

	/**
	 * <p>Constructor for PlaceEditCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a Type object.
	 * @param placeId a {@link java.lang.String} object.
	 * @param marking a int.
	 */
	public PlaceEditCommand(final Object source, final Type type, final String placeId, final int marking) {
		super(source, type, placeId, marking);
	}

}
