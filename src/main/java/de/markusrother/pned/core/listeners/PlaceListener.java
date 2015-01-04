package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.events.PlaceEventObject;

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
	 * @param evt a {@link de.markusrother.pned.core.events.PlaceEventObject} object.
	 */
	void setMarking(PlaceEventObject evt);

}
