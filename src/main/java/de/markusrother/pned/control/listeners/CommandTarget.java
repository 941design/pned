package de.markusrother.pned.control.listeners;

import de.markusrother.pned.gui.listeners.NodeRemovalListener;

/**
 * <p>
 * Aggregate interface for command listeners. This combines all listeners
 * necessary to maintain a consistent Petri net.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface CommandTarget
	extends
		PetriNetIOListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeMotionListener,
		NodeRemovalListener,
		PlaceListener,
		LabelEditListener,
		TransitionListener {

	// NOTHING

}
