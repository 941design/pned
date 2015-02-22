package de.markusrother.pned.control.commands;

import java.util.EventListener;

import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;

/**
 * <p>
 * EdgeCreationListener interface.
 * </p>
 *
 * TODO - rename to EdgeListener and merge with other operations.
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface EdgeCreationListener
    extends
        EventListener {

    /**
     * <p>
     * createEdge.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.EdgeCreationCommand}
     *            object.
     */
    void createEdge(EdgeCreationCommand cmd);

    /**
     * <p>
     * removeEdge.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.gui.control.commands.EdgeRemoveCommand}
     *            object.
     */
    void removeEdge(EdgeRemoveCommand cmd);

}
