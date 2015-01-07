package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;

/**
 * <p>
 * EdgeCreationListener interface.
 * </p>
 * 
 * FIXME - rename to EdgeListener
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeCreationListener
	extends
		EventListener {

	/**
	 * <p>
	 * createEdge.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.core.commands.EdgeCreationCommand}
	 *            object.
	 */
	void createEdge(EdgeCreationCommand cmd);

	void removeEdge(EdgeRemoveCommand cmd);

}
