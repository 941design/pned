package de.markusrother.pned.gui.components.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;

/**
 * <p>
 * NodeRemovalListener interface.
 * </p>
 * 
 * TODO - merge both events.
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeRemovalListener
	extends
		EventListener {

	/**
	 * <p>
	 * nodeRemoved.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.control.commands.NodeRemovalCommand}
	 *            object.
	 */
	void nodeRemoved(NodeRemovalCommand cmd);

	/**
	 * <p>
	 * removeSelectedNodes.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent}
	 *            object.
	 */
	void removeSelectedNodes(RemoveSelectedNodesEvent cmd);

}
