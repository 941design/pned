package de.markusrother.pned.control.commands;

import java.io.IOException;
import java.util.EventListener;

/**
 * <p>
 * PetriNetIOListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface PetriNetIOListener
    extends
        EventListener {

    /**
     * <p>
     * setCurrentDirectory.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.PetriNetIOCommand}
     *            object.
     */
    void setCurrentDirectory(PetriNetIOCommand cmd);

    /**
     * <p>
     * importPnml.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.PetriNetIOCommand}
     *            object.
     * @throws java.io.IOException
     *             if any.
     */
    void importPnml(PetriNetIOCommand cmd) throws IOException;

    /**
     * <p>
     * exportPnml.
     * </p>
     *
     * @param cmd
     *            a
     *            {@link de.markusrother.pned.control.commands.PetriNetIOCommand}
     *            object.
     * @throws java.io.IOException
     *             if any.
     */
    void exportPnml(PetriNetIOCommand cmd) throws IOException;

}
