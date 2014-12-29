package de.markusrother.pned.commands;

import java.awt.event.ActionEvent;
import java.io.File;

public class PetriNetIOCommand extends ActionEvent {

	private final File file;

	public PetriNetIOCommand(final Object source, final File file) {
		super(source, ActionEvent.ACTION_PERFORMED, "");
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}
