package de.markusrother.pned.gui.control.commands;

import de.markusrother.pned.control.commands.CommandTarget;

/**
 * <p>
 * Aggregate interface combining action listeners for commands.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface PnCommandTarget
    extends
        CommandTarget,
        LayoutCommandTarget,
        PetriNetListener,
        NodeListener {

    // NOTHING

}
