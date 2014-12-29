package de.markusrother.pned.core;

import de.markusrother.pned.commands.listeners.TransitionActivationListener;

public interface TransitionActivationEventSource {

	void addTransitionActivationListener(TransitionActivationListener l);

	void removeTransitionActivationListener(TransitionActivationListener l);

}
