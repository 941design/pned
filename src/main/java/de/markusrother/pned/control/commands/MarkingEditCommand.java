package de.markusrother.pned.control.commands;

import de.markusrother.pned.control.events.MarkingEventObject;

/**
 * <p>
 * Instances of this class trigger
 * {@link de.markusrother.pned.core.model.PlaceModel} manipulation.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.commands.MarkingEditListener
 * @see de.markusrother.pned.control.events.MarkingChangeEvent
 */
public class MarkingEditCommand extends MarkingEventObject {

	/**
	 * <p>
	 * Constructor for MarkingEditCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param placeId
	 *            a {@link java.lang.String} - the unique identifier of the
	 *            place to be edited.
	 * @param marking
	 *            a int - the edited place's new marking.
	 */
	public MarkingEditCommand(final Object source, final String placeId, final int marking) {
		super(source, placeId, marking);
	}

}
