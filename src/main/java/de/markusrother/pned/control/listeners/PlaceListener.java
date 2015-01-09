package de.markusrother.pned.control.listeners;

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
public interface PlaceListener
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
	void setMarking(MarkingEventObject evt);

}
