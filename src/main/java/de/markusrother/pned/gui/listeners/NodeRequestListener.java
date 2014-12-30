package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeRequest;

/**
 * <p>NodeRequestListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeRequestListener
	extends
		EventListener {

	/**
	 * <p>requestNode.</p>
	 *
	 * @param req a {@link de.markusrother.pned.gui.events.NodeRequest} object.
	 */
	void requestNode(NodeRequest req);

}
