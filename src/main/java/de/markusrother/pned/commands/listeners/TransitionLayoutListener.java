package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.TransitionLayoutCommand;

public interface TransitionLayoutListener
	extends
		EventListener {

	void setSize(TransitionLayoutCommand cmd);
}
