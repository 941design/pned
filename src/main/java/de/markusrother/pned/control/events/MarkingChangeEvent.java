package de.markusrother.pned.control.events;

/**
 * <p>
 * Event that occurs, when a {@link de.markusrother.pned.core.model.PlaceModel}s
 * marking has changed due to transition activation.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.commands.MarkingEditCommand
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.events.MarkingEventListener
 */
public class MarkingChangeEvent extends MarkingEventObject {

	/**
	 * <p>
	 * Constructor for MarkingChangeEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param placeId
	 *            a {@link java.lang.String} - the unique identifier of the
	 *            place to be changed.
	 * @param marking
	 *            a int - the new marking.
	 */
	public MarkingChangeEvent(final Object source, final String placeId, final int marking) {
		super(source, placeId, marking);
	}

}
