package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.events.TransitionActivationEvent;

/**
 * <p>TransitionActivationListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionActivationListener
	extends
		EventListener {

	/**
	 * <p>transitionActivated.</p>
	 *
	 * @param e a {@link de.markusrother.pned.core.events.TransitionActivationEvent} object.
	 */
	void transitionActivated(TransitionActivationEvent e);

	/**
	 * <p>transitionDeactivated.</p>
	 *
	 * @param e a {@link de.markusrother.pned.core.events.TransitionActivationEvent} object.
	 */
	void transitionDeactivated(TransitionActivationEvent e);

}
