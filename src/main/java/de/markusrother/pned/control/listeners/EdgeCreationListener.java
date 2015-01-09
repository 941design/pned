package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
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
	 *            {@link de.markusrother.pned.control.commands.EdgeCreationCommand}
	 *            object.
	 */
	void createEdge(EdgeCreationCommand cmd);

	/**
	 * <p>removeEdge.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.gui.commands.EdgeRemoveCommand} object.
	 */
	void removeEdge(EdgeRemoveCommand cmd);

}
