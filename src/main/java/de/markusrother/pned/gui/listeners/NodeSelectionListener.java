package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeSelectionEvent;

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
	 * @param e a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodesSelected(NodeSelectionEvent e);

	/**
	 * <p>nodesUnselected.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodesUnselected(NodeSelectionEvent e);

	/**
	 * <p>nodeSelectionFinished.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodeSelectionFinished(NodeSelectionEvent e);

	/**
	 * <p>nodeSelectionCancelled.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodeSelectionCancelled(NodeSelectionEvent e);

}
