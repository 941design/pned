package de.markusrother.pned.gui.control.commands;

import java.util.EventObject;

import de.markusrother.pned.gui.core.NodeCreationMode;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * SetNodeTypeCommand class.
 * </p>
 *
 * FIXME - merge into PetriNetCommand
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.commands.NodeListener
 */
public class SetNodeTypeCommand extends EventObject
	implements
		JsonSerializable {

	private final NodeCreationMode mode;

	/**
	 * <p>
	 * Constructor for SetNodeTypeCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param mode
	 *            a {@link de.markusrother.pned.gui.core.NodeCreationMode}
	 *            object.
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
	 * @return a {@link de.markusrother.pned.gui.core.NodeCreationMode} object.
	 */
	public NodeCreationMode getMode() {
		return mode;
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
		return builder.append("type", mode.name()) //
				.toString();
	}

}
