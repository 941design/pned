package de.markusrother.pned.gui.events;

import java.util.EventObject;

import de.markusrother.pned.gui.NodeCreationMode;

/**
 * <p>
 * SetNodeTypeCommand class.
 * </p>
 *
 * FIXME - merge into PetriNetCommand
 * 
 * @author Markus Rother
 * @version 1.0
 */
public class SetNodeTypeCommand extends EventObject {

	private final NodeCreationMode mode;

	/**
	 * <p>
	 * Constructor for SetNodeTypeCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param mode
	 *            a {@link de.markusrother.pned.gui.NodeCreationMode} object.
	 */
	public SetNodeTypeCommand(final Object source, final NodeCreationMode mode) {
		super(source);
		this.mode = mode;
	}

	/**
	 * <p>
	 * Getter for the field <code>mode</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.NodeCreationMode} object.
	 */
	public NodeCreationMode getMode() {
		return mode;
	}

}
