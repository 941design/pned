package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;

/**
 * Listener to observe node selection events
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeSelectionListener
	extends
		EventListener {

	/**
	 * <p>nodesSelected.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeMultiSelectionEvent} object.
	 */
	public void nodesSelected(NodeMultiSelectionEvent e);

	/**
	 * <p>nodesUnselected.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeMultiSelectionEvent} object.
	 */
	public void nodesUnselected(NodeMultiSelectionEvent e);

	/**
	 * <p>nodeSelectionFinished.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeMultiSelectionEvent} object.
	 */
	public void nodeSelectionFinished(NodeMultiSelectionEvent e);

	/**
	 * <p>nodeSelectionCancelled.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeMultiSelectionEvent} object.
	 */
	public void nodeSelectionCancelled(NodeMultiSelectionEvent e);

}
