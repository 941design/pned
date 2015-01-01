package de.markusrother.pned.core.control;

import de.markusrother.pned.core.listeners.TransitionActivationListener;

/**
 * <p>
 * TransitionActivationEventSource interface.
 * </p>
 *
 * FIXME - rename to EventSource
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionActivationEventSource {

	/**
	 * <p>
	 * addTransitionActivationListener.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.core.listeners.TransitionActivationListener}
	 *            object.
	 */
	void addTransitionActivationListener(TransitionActivationListener l);

	/**
	 * <p>
	 * removeTransitionActivationListener.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.core.listeners.TransitionActivationListener}
	 *            object.
	 */
	void removeTransitionActivationListener(TransitionActivationListener l);

}
