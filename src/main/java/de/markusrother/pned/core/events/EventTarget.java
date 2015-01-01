package de.markusrother.pned.core.events;

import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

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
		PetriNetListener,
		PetriNetIOListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeMotionListener,
		PlaceEditListener,
		LabelEditListener {

}
