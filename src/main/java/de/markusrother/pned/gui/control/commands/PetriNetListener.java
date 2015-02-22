package de.markusrother.pned.gui.control.commands;

import java.util.EventListener;

/**
 * <p>PetriNetListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNetListener
    extends
        EventListener {

    /**
     * <p>disposePetriNet.</p>
     *
     * @param cmd a {@link de.markusrother.pned.gui.control.commands.PetriNetEditCommand} object.
     */
    void createPetriNet(PetriNetEditCommand cmd);

}
