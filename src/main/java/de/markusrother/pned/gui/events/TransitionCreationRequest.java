package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

public class TransitionCreationRequest extends ActionEvent {

	public TransitionCreationRequest(final Object source) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
	}

}
