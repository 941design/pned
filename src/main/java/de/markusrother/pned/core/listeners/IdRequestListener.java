package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.requests.IdRequest;

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
	 * @param req a {@link de.markusrother.pned.core.requests.IdRequest} object.
	 */
	void requestId(IdRequest req);

}
