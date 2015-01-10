package de.markusrother.pned.gui.control.requests;

import de.markusrother.pned.control.requests.RequestTarget;

/**
 * <p>
 * Aggregate interface for request listeners. This combines all listeners
 * necessary to answer requests concerning a Petri net's state and visualization
 * thereof.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface PnRequestTarget
	extends
		RequestTarget,
		NodeRequestListener {

	// NOTHING

}
