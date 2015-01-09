package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.requests.IdRequest;

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
	 * @param req a {@link de.markusrother.pned.control.requests.IdRequest} object.
	 */
	void requestId(IdRequest req);

}
