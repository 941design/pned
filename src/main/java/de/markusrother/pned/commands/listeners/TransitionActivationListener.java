package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.events.TransitionActivationEvent;

public interface TransitionActivationListener
	extends
		EventListener {

	void transitionActivated(TransitionActivationEvent e);

	void transitionDeactivated(TransitionActivationEvent e);

}
