package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

public class PlaceCreationRequest extends ActionEvent {

	public PlaceCreationRequest(final Object source) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
	}

}
