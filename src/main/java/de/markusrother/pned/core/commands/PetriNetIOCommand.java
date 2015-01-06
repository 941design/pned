package de.markusrother.pned.core.commands;

import java.io.File;
import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * PetriNetIOCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetIOCommand extends EventObject
	implements
		JsonSerializable {

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
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.core.commands.PetriNetIOCommand.Type}
	 *            object.
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
