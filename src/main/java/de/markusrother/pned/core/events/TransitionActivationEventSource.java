package de.markusrother.pned.core.events;

import de.markusrother.pned.core.listeners.TransitionActivationListener;

/**
 * <p>TransitionActivationEventSource interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionActivationEventSource {

	/**
	 * <p>addTransitionActivationListener.</p>
	 *
	 * @param l a {@link de.markusrother.pned.core.listeners.TransitionActivationListener} object.
	 */
	void addTransitionActivationListener(TransitionActivationListener l);

	/**
	 * <p>removeTransitionActivationListener.</p>
	 *
	 * @param l a {@link de.markusrother.pned.core.listeners.TransitionActivationListener} object.
	 */
	void removeTransitionActivationListener(TransitionActivationListener l);

}
