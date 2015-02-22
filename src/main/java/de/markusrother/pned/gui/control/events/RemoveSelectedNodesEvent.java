package de.markusrother.pned.gui.control.events;

import java.util.EventObject;

/**
 * <p>
 * RemoveSelectedNodesEvent class.
 * </p>
 *
 * TODO - Should become single RemoveNodeEvent with id!
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.components.listeners.NodeRemovalListener
 */
public class RemoveSelectedNodesEvent extends EventObject {

    /**
     * <p>
     * Constructor for RemoveSelectedNodesEvent.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} object.
     */
    public RemoveSelectedNodesEvent(final Object source) {
        super(source);
    }

}
