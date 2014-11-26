package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeSelectionEvent;

/**
 * Listener to observe node selection events
 *
 */
public interface NodeSelectionListener
	extends
		EventListener {

	public void nodesSelected(NodeSelectionEvent event);

	public void nodesUnselected(NodeSelectionEvent event);

	public void nodeSelectionCancelled(NodeSelectionEvent event);

}
