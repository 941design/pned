package de.markusrother.pned.control.requests;

import java.util.EventListener;

import de.markusrother.pned.control.EventBus;

/**
 * <p>
 * IdRequestListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see EventBus
 */
public interface IdRequestListener
	extends
		EventListener {

	/**
	 * <p>
	 * requestId.
	 * </p>
	 *
	 * @param req
	 *            a {@link de.markusrother.pned.control.requests.IdRequest}
	 *            object.
	 */
	void requestId(IdRequest req);

}
