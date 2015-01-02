package de.markusrother.pned.gui.commands;

import java.util.EventObject;

/**
 * <p>
 * PetriNetEditCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetEditCommand extends EventObject {

	public enum Type {
		NEW
	}

	private final Type type;

	/**
	 * <p>
	 * Constructor for PetriNetEditCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param type a {@link de.markusrother.pned.gui.commands.PetriNetEditCommand.Type} object.
	 */
	public PetriNetEditCommand(final Object source, final Type type) {
		super(source);
		this.type = type;
	}

}
