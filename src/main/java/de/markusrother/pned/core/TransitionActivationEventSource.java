package de.markusrother.pned.core;

public interface TransitionActivationEventSource {

	void addTransitionActivationListener(TransitionActivationListener l);

	void removeTransitionActivationListener(TransitionActivationListener l);

}
