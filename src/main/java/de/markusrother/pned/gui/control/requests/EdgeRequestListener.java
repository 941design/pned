package de.markusrother.pned.gui.control.requests;

import java.util.EventListener;

/**
 * <p>EdgeRequestListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeRequestListener
	extends
		EventListener {

	/**
	 * <p>requestEdge.</p>
	 *
	 * @param req a {@link de.markusrother.pned.gui.control.requests.EdgeRequest} object.
	 */
	void requestEdge(EdgeRequest req);

}
