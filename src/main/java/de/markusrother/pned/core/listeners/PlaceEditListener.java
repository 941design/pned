package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.PlaceEditEvent;

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
	 * @param cmd a {@link de.markusrother.pned.core.commands.PlaceEditEvent} object.
	 */
	void setMarking(PlaceEditEvent cmd);

}
