package de.markusrother.pned.gui.events;

import de.markusrother.pned.core.listeners.CommandTarget;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.PetriNetListener;

/**
 * <p>
 * GuiEventTarget interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface GuiEventTarget
	extends
		// Inherited event listeners:
		CommandTarget,
		//
		PetriNetListener,
		NodeListener,
		NodeRequestListener,
		// Layout listeners:
		PlaceLayoutListener,
		TransitionLayoutListener,
		EdgeLayoutListener,
		MarkingLayoutListener {

}
