package de.markusrother.pned.control.commands;

import java.util.EventListener;

/**
 * <p>
 * NodeCreationListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface NodeCreationListener
    extends
        EventListener {

    /**
     * <p>
     * createPlace.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.PlaceCreationCommand}
     *            object.
     */
    void createPlace(PlaceCreationCommand cmd);

    /**
     * <p>
     * createTransition.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.TransitionCreationCommand}
     *            object.
     */
    void createTransition(TransitionCreationCommand cmd);

}
