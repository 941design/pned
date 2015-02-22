package de.markusrother.pned.control.commands;

import java.awt.Point;

/**
 * <p>
 * Instances of this class trigger creation of new
 * {@link de.markusrother.pned.core.model.TransitionModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.commands.NodeCreationListener
 */
public class TransitionCreationCommand extends AbstractNodeCreationCommand {

    /**
     * <p>
     * Constructor for TransitionCreationCommand.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} - this event's source.
     * @param nodeId
     *            a {@link java.lang.String} - the unique identifier of the
     *            transition.
     * @param point
     *            a {@link java.awt.Point} - the location at which to create the
     *            new transition.
     */
    public TransitionCreationCommand(final Object source, final String nodeId, final Point point) {
        super(source, nodeId, point);
    }

}
