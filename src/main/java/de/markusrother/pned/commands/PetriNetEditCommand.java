package de.markusrother.pned.commands;

import java.awt.event.ActionEvent;

public class PetriNetEditCommand extends ActionEvent {

	public PetriNetEditCommand(final Object source) {
		super(source, ActionEvent.ACTION_PERFORMED, null);
	}

}
