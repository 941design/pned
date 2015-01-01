package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Value object representing the element's labels, e.g. of nodes. Used for
 * marshalling to pnml (xml).
 * </p>
 *
 * <pre>
 * ...
 * &lt;name&gt;
 * 	&lt;text&gt;foobar&lt;/text&gt;
 * 	&lt;graphics&gt;
 * 		&lt;offset x="23" y="42"/&gt;
 * 	&lt;/graphics&gt;
 * &lt;/name&gt;
 * ...
 * </pre>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 * @see LabelMarshaller
 */
@XmlType(propOrder = { "label", "graphics" })
public class LabelVO {

	private final String label;
	private final RelativeGraphicsVO graphics;

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	private @SuppressWarnings("unused") LabelVO() {
		// IGNORE
		this.label = null;
		this.graphics = null;
	}

	/**
	 * <p>
	 * Constructor for LabelVO.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} - the textual content.
	 */
	public LabelVO(final String label) {
		this.label = label;
		this.graphics = new RelativeGraphicsVO(new Point()); // FIXME
	}

	/**
	 * <p>
	 * Getter for the field <code>label</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the textual content.
	 */
	@XmlElement(name = "text")
	public String getLabel() {
		return label;
	}

	/**
	 * <p>
	 * Getter for the field <code>graphics</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.io.RelativeGraphicsVO} object,
	 *         representing the label's coordinates relative to its element.
	 */
	@XmlElement(name = "graphics")
	public RelativeGraphicsVO getGraphics() {
		return graphics;
	}
}
