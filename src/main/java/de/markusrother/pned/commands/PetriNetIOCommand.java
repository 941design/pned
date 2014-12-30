package de.markusrother.pned.commands;

import java.io.File;
import java.util.EventObject;

public class PetriNetIOCommand extends EventObject {

	private final File file;

	public PetriNetIOCommand(final Object source, final File file) {
		super(source);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}
