package de.markusrother.pned.gui.control.commands;

/**
 * <p>
 * Aggregate interface for command listeners. This combines all listeners
 * necessary to maintain a consistent Petri net and visualization thereof.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface LayoutCommandTarget
    extends
        PlaceLayoutListener,
        TransitionLayoutListener,
        EdgeLayoutListener,
        MarkingLayoutListener {

    // NOTHING

}
