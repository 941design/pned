package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

public interface NodeCreationListener
	extends
		EventListener {

	void createPlace(PlaceCreationCommand cmd);

	void createTransition(TransitionCreationCommand cmd);

}
