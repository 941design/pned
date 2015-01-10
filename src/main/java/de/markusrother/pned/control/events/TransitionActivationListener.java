package de.markusrother.pned.control.events;

import java.util.EventListener;

/**
 * <p>
 * TransitionActivationListener interface.
 * </p>
 *
 * TODO - merge with TransitionListener
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface TransitionActivationListener
	extends
		EventListener {

	/**
	 * <p>
	 * transitionActivated.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.control.events.TransitionActivationEvent}
	 *            object.
	 */
	void transitionActivated(TransitionActivationEvent e);

	/**
	 * <p>
	 * transitionDeactivated.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.control.events.TransitionActivationEvent}
	 *            object.
	 */
	void transitionDeactivated(TransitionActivationEvent e);

}
