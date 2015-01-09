package de.markusrother.pned.control.commands;

import java.io.File;
import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Instances of this class trigger file operations on
 * {@link de.markusrother.pned.core.model.PetriNetModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetIOCommand extends EventObject
	implements
		JsonSerializable {

	/**
	 * The type of {@link PetriNetIOCommand}.
	 */
	public enum Type {
		/** Opens a new Petri net from a given file. */
		OPEN,
		/** Saves the current Petri net to a given file. */
		SAVE,
		/** Changed the working directory to a given file. */
		CWD
	}

	/** The type of requested change. */
	private final Type type;
	/** The file on which to execute the requested operation. */
	private final File file;

	/**
	 * <p>
	 * Constructor for PetriNetIOCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param file
	 *            a {@link java.io.File} - the file on which to execute the
	 *            requested operation.
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.control.commands.PetriNetIOCommand.Type}
	 *            - the type of requested change.
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
	 * @return a {@link java.io.File} - the file on which to execute the
	 *         requested operation.
	 */
	public File getFile() {
		return file;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("type", type.name()) //
				.append("file", file) //
				.toString();
	}

}
