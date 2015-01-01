package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.NodeRemovalEvent;
import de.markusrother.pned.core.commands.RemoveSelectedNodesEvent;

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
	 * @param e
	 *            a {@link de.markusrother.pned.core.commands.NodeRemovalEvent}
	 *            object.
	 */
	void nodeRemoved(NodeRemovalEvent e);

	/**
	 * <p>
	 * removeSelectedNodes.
	 * </p>
	 *
	 * FIXME - separate
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.core.commands.RemoveSelectedNodesEvent}
	 *            object.
	 */
	void removeSelectedNodes(RemoveSelectedNodesEvent e);

}
