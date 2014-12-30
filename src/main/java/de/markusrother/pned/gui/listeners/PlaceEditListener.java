package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PlaceEditEvent;

/**
 * <p>PlaceEditListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PlaceEditListener
	extends
		EventListener {

	/**
	 * <p>setMarking.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.gui.events.PlaceEditEvent} object.
	 */
	void setMarking(PlaceEditEvent cmd);

}
