package de.markusrother.pned.core.control;

import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceListener;
import de.markusrother.pned.core.listeners.TransitionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;

/**
 * <p>
 * Super/Compound interface for command listeners.
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

}
