package de.markusrother.pned.core.events;

import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.core.listeners.TransitionActivationListener;

/**
 * <p>
 * EventTarget interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EventTarget
	extends
		PetriNetIOListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeMotionListener,
		PlaceEditListener,
		LabelEditListener,
		TransitionActivationListener {

}
