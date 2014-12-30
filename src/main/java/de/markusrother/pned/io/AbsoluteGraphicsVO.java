package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>
 * Value object representing the graphics pnml tag of absolutely positioned
 * elements, e.g. nodes. Used for marshalling to pnml (xml).
 * </p>
 * 
 * <pre>
 * ...
 * &lt;graphics&gt;
 * 	&lt;position x="23" y="42"/&gt;
 * &lt;/graphics&gt;
 * ...
 * </pre>
 * 
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 * @see AbsolutePositionMarshaller
 */
public class AbsoluteGraphicsVO extends AbstractGraphicsVO {

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	private @SuppressWarnings("unused") AbsoluteGraphicsVO() {
		// IGNORE
	}

	/**
	 * <p>
	 * Constructor for AbsoluteGraphicsVO.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} representing the graphic's absolute
	 *            coordinates.
	 */
	public AbsoluteGraphicsVO(final Point point) {
		super(point);
	}

	/** {@inheritDoc} */
	@Override
	@XmlElement(name = "position")
	public LocationVO getLocation() {
		return location;
	}

}
