package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.EdgeCreationCommand;

/**
 * <p>EdgeCreationListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeCreationListener
	extends
		EventListener {

	/**
	 * <p>createEdge.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.gui.events.EdgeCreationCommand} object.
	 */
	void createEdge(EdgeCreationCommand cmd);

}
