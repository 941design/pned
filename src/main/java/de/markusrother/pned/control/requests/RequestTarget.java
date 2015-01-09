package de.markusrother.pned.control.requests;

import de.markusrother.pned.control.EventBus;

/**
 * <p>
 * Aggregate interface for request listeners. This combines all listeners
 * necessary to answer requests concerning a Petri net's state.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see EventBus
 */
public interface RequestTarget
	extends
		IdRequestListener {

	// NOTHING

}
