package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.events.TransitionActivationEvent;

/**
 * <p>
 * TransitionActivationListener interface.
 * </p>
 *
 * FIXME - Create TransitionActivationCommand
 *
 * FIXME - merge with TransitionListener
 *
 * @author Markus Rother
 * @version 1.0
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
