package de.markusrother.pned.core.commands;

import java.util.EventObject;

/**
 * <p>
 * LabelEditEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelEditCommand extends EventObject {

	public enum Type {
		SET_LABEL
	}

	private final String elementId;
	private final String label;
	private final Type type;

	/**
	 * <p>
	 * Constructor for LabelEditEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param elementId
	 *            a {@link java.lang.String} object.
	 * @param label
	 *            a {@link java.lang.String} object.
	 */
	public LabelEditCommand(final Object source, final Type type, final String elementId, final String label) {
		super(source);
		this.type = type;
		this.elementId = elementId;
		this.label = label;
	}

	/**
	 * <p>
	 * Getter for the field <code>elementId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * <p>
	 * Getter for the field <code>label</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return label;
	}

}
