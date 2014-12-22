package de.markusrother.pned.core;

import java.util.EventListener;

public interface TransitionActivationListener
	extends
		EventListener {

	void transitionActivated(TransitionActivationEvent e);

	void transitionDeactivated(TransitionActivationEvent e);

}
