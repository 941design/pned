package de.markusrother.pned.gui.events;

import java.util.EventObject;

/**
 * <p>LabelEditEvent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelEditEvent extends EventObject {

	private final String elementId;
	private final String label;

	/**
	 * <p>Constructor for LabelEditEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param elementId a {@link java.lang.String} object.
	 * @param label a {@link java.lang.String} object.
	 */
	public LabelEditEvent(final Object source, final String elementId, final String label) {
		super(source);
		this.elementId = elementId;
		this.label = label;
	}

	/**
	 * <p>Getter for the field <code>elementId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getElementId() {
		return elementId;
	}

	/**
	 * <p>Getter for the field <code>label</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return label;
	}

}
