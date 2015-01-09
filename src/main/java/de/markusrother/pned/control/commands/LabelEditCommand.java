package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Instances of this class trigger
 * {@link de.markusrother.pned.core.model.NodeModel} label manipulation.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelEditCommand extends EventObject
	implements
		JsonSerializable {

	/** The associated element's unique identifier. */
	private final String elementId;
	/** The newly assigned label */
	private final String label;

	/**
	 * <p>
	 * Constructor for LabelEditEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param elementId
	 *            a {@link java.lang.String} - the associated element's unique
	 *            identifier.
	 * @param label
	 *            a {@link java.lang.String} - the newly assigned label.
	 */
	public LabelEditCommand(final Object source, final String elementId, final String label) {
		super(source);
		this.elementId = elementId;
		this.label = label;
	}

	/**
	 * <p>
	 * Getter for the field <code>elementId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the associated element's unique
	 *         identifier.
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * <p>
	 * Getter for the field <code>label</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the newly assigned label.
	 */
	public String getLabel() {
		return label;
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
		return builder.append("elementId", elementId) //
				.append("label", label) //
				.toString();
	}

}
