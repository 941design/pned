package de.markusrother.pned.gui.control.requests;

import java.util.EventListener;

/**
 * <p>LabelRequestListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface LabelRequestListener
	extends
		EventListener {

	/**
	 * <p>requestLabel.</p>
	 *
	 * @param req a {@link de.markusrother.pned.gui.control.requests.LabelRequest} object.
	 */
	void requestLabel(LabelRequest req);

}
