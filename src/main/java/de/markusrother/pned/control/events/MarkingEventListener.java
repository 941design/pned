package de.markusrother.pned.control.events;

import java.util.EventListener;

public interface MarkingEventListener
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
	void setMarking(MarkingChangeEvent evt);

}
