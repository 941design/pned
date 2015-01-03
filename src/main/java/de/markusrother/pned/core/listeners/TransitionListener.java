package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.TransitionExecutionCommand;

public interface TransitionListener
	extends
		EventListener {

	void fireTransition(TransitionExecutionCommand cmd);

}
