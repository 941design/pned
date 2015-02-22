package de.markusrother.pned.gui.control.events;

import de.markusrother.pned.control.events.EventTarget;

/**
 * <p>
 * Aggregate interface for event listeners. This combines all listeners
 * necessary to respond to events that occur within a Petri net and
 * visualization thereof.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface PnEventTarget
    extends
        EventTarget,
        NodeSelectionListener,
        EdgeEditListener {

    // NOTHING

}
