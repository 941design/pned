package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;

/**
 * <p>
 * NodeRemovalListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeRemovalListener
	extends
		EventListener {

	// TODO - What is the difference, exactly?

	/**
	 * <p>
	 * nodeRemoved.
	 * </p>
	 *
	 * @param cmd
	 *            a {@link de.markusrother.pned.control.commands.NodeRemovalCommand}
	 *            object.
	 */
	void nodeRemoved(NodeRemovalCommand cmd);

	/**
	 * <p>
	 * removeSelectedNodes.
	 * </p>
	 *
	 * FIXME - separate
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.gui.events.RemoveSelectedNodesEvent}
	 *            object.
	 */
	void removeSelectedNodes(RemoveSelectedNodesEvent cmd);

}
