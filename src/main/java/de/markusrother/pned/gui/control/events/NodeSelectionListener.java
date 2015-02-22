package de.markusrother.pned.gui.control.events;

import java.util.EventListener;

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
     * @param e a {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent} object.
     */
    public void nodesSelected(NodeMultiSelectionEvent e);

    /**
     * <p>nodesUnselected.</p>
     *
     * @param e a {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent} object.
     */
    public void nodesUnselected(NodeMultiSelectionEvent e);

    /**
     * <p>nodeSelectionFinished.</p>
     *
     * @param e a {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent} object.
     */
    public void nodeSelectionFinished(NodeMultiSelectionEvent e);

    /**
     * <p>nodeSelectionCancelled.</p>
     *
     * @param e a {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent} object.
     */
    public void nodeSelectionCancelled(NodeMultiSelectionEvent e);

}
