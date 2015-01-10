package de.markusrother.pned.control.events;


/**
 * <p>
 * Aggregate interface for event listeners. This combines all listeners
 * necessary to respond to events that occur within a Petri net.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public interface EventTarget
	extends
		MarkingEventListener,
		TransitionActivationListener {

	// NOTHING

}
