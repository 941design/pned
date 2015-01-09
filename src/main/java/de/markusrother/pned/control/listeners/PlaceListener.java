package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.events.PlaceEventObject;

/**
 * <p>
 * PlaceEditListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PlaceListener
	extends
		EventListener {

	/**
	 * <p>
	 * setMarking.
	 * </p>
	 *
	 * @param evt a {@link de.markusrother.pned.control.events.PlaceEventObject} object.
	 */
	void setMarking(PlaceEventObject evt);

}
