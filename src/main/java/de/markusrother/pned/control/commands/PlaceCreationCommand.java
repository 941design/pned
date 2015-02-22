package de.markusrother.pned.control.commands;

import java.awt.Point;

/**
 * <p>
 * Instances of this class trigger creation of new
 * {@link de.markusrother.pned.core.model.PlaceModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.commands.NodeCreationListener
 */
public class PlaceCreationCommand extends AbstractNodeCreationCommand {

    /**
     * <p>
     * Constructor for PlaceCreationCommand.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} - this event's source.
     * @param placeId
     *            a {@link java.lang.String} - the unique identifier of the
     *            place to be created.
     * @param point
     *            a {@link java.awt.Point} - the location at which to create the
     *            new place.
     */
    public PlaceCreationCommand(final Object source, final String placeId, final Point point) {
        super(source, placeId, point);
    }

}
