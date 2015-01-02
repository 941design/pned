package de.markusrother.pned.core.commands;

import java.io.File;
import java.util.EventObject;

/**
 * <p>
 * PetriNetIOCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetIOCommand extends EventObject {

	public enum Type {
		OPEN,
		SAVE,
		STAT
	}

	private final Type type;
	private final File file;

	/**
	 * <p>
	 * Constructor for PetriNetIOCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param file
	 *            a {@link java.io.File} object.
	 */
	public PetriNetIOCommand(final Object source, final Type type, final File file) {
		super(source);
		this.type = type;
		this.file = file;
	}

	/**
	 * <p>
	 * Getter for the field <code>file</code>.
	 * </p>
	 *
	 * @return a {@link java.io.File} object.
	 */
	public File getFile() {
		return file;
	}

}
