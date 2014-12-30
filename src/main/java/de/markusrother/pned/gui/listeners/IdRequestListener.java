package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.IdRequest;

/**
 * <p>IdRequestListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface IdRequestListener
	extends
		EventListener {

	/**
	 * <p>requestId.</p>
	 *
	 * @param req a {@link de.markusrother.pned.gui.events.IdRequest} object.
	 */
	void requestId(IdRequest req);

}
