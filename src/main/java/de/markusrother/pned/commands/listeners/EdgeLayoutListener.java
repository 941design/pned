package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.EdgeLayoutCommand;

public interface EdgeLayoutListener
	extends
		EventListener {

	void setSize(EdgeLayoutCommand cmd);

}
