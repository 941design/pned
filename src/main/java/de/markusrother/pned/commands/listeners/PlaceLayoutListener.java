package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.PlaceLayoutCommand;

public interface PlaceLayoutListener
	extends
		EventListener {

	void setSize(PlaceLayoutCommand cmd);

}
