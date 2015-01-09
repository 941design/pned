package de.markusrother.pned.control.events;

import java.util.EventListener;

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
