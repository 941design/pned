package de.markusrother.pned.control.commands;

import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;

/**
 * <p>
 * Aggregate interface for command listeners. This combines all listeners
 * necessary to maintain a consistent Petri net.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface CommandTarget
    extends
        PetriNetIOListener,
        NodeCreationListener,
        EdgeCreationListener,
        NodeMotionListener,
        NodeRemovalListener,
        MarkingEditListener,
        LabelEditListener,
        TransitionListener {

    // NOTHING

}
