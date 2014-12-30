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
	 * @param event a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodesSelected(NodeSelectionEvent event);

	/**
	 * <p>nodesUnselected.</p>
	 *
	 * @param event a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodesUnselected(NodeSelectionEvent event);

	/**
	 * <p>nodeSelectionFinished.</p>
	 *
	 * @param event a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodeSelectionFinished(NodeSelectionEvent event);

	/**
	 * <p>nodeSelectionCancelled.</p>
	 *
	 * @param event a {@link de.markusrother.pned.gui.events.NodeSelectionEvent} object.
	 */
	public void nodeSelectionCancelled(NodeSelectionEvent event);

}
