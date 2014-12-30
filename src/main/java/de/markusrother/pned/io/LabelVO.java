package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>LabelVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlType(propOrder = { "label", "graphics" })
public class LabelVO {

	private final String label;
	private final RelativeGraphicsVO graphics;

	/**
	 * <p>Constructor for LabelVO.</p>
	 */
	private @SuppressWarnings("unused") LabelVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.label = null;
		this.graphics = null;
	}

	/**
	 * <p>Constructor for LabelVO.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 */
	public LabelVO(final String label) {
		this.label = label;
		this.graphics = new RelativeGraphicsVO(new Point()); // TODO
	}

	/**
	 * <p>Getter for the field <code>label</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@XmlElement(name = "text")
	public String getLabel() {
		return label;
	}

	/**
	 * <p>Getter for the field <code>graphics</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.io.RelativeGraphicsVO} object.
	 */
	@XmlElement(name = "graphics")
	public RelativeGraphicsVO getGraphics() {
		return graphics;
	}
}
