package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.TransitionCreationRequest;

public interface NodeCreationListener
	extends
		EventListener {

	void createPlace(PlaceCreationRequest e);

	void createTransition(TransitionCreationRequest e);

}
