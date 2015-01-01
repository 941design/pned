package de.markusrother.pned.gui.events;

import de.markusrother.pned.commands.listeners.EdgeLayoutListener;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;
import de.markusrother.pned.core.events.EventTarget;

/**
 * <p>GuiEventTarget interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface GuiEventTarget
	extends
		EventTarget,
		PlaceLayoutListener,
		TransitionLayoutListener,
		EdgeLayoutListener,
		MarkingLayoutListener {

}
