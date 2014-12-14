package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.MarkingLayoutCommand;

public interface MarkingLayoutListener
	extends
		EventListener {

	void setSize(MarkingLayoutCommand cmd);

}
