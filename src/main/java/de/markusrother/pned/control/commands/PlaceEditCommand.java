package de.markusrother.pned.control.commands;

import de.markusrother.pned.control.events.PlaceEventObject;

/**
 * <p>
 * Instances of this class trigger
 * {@link de.markusrother.pned.core.model.PlaceModel} manipulation.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceEditCommand extends PlaceEventObject {

	/**
	 * <p>
	 * Constructor for PlaceEditCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param type
	 *            a {@link Type} - the type of manipulation.
	 * @param placeId
	 *            a {@link java.lang.String} - the unique identifier of the
	 *            place to be edited.
	 * @param marking
	 *            a int - the edited place's new marking.
	 */
	public PlaceEditCommand(final Object source, final Type type, final String placeId, final int marking) {
		super(source, type, placeId, marking);
	}

}
