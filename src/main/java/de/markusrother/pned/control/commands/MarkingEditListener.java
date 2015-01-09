package de.markusrother.pned.control.commands;

import java.util.EventListener;

import de.markusrother.pned.control.events.MarkingEventObject;

/**
 * <p>
 * PlaceEditListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface MarkingEditListener
	extends
		EventListener {

	/**
	 * <p>
	 * setMarking.
	 * </p>
	 *
	 * @param evt
	 *            a
	 *            {@link de.markusrother.pned.control.events.MarkingEventObject}
	 *            object.
	 */
	void setMarking(MarkingEditCommand evt);

}
