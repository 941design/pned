package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;

/**
 * <p>NodeRemovalListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeRemovalListener
	extends
		EventListener {

	// TODO - What is the difference, exactly?

	/**
	 * <p>nodeRemoved.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeRemovalEvent} object.
	 */
	void nodeRemoved(NodeRemovalEvent e);

	/**
	 * <p>removeSelectedNodes.</p>
	 *
	 * @param e a {@link de.markusrother.pned.events.RemoveSelectedNodesEvent} object.
	 */
	void removeSelectedNodes(RemoveSelectedNodesEvent e);

}
