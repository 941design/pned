package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>AbsoluteGraphicsVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbsoluteGraphicsVO extends AbstractGraphicsVO {

	/**
	 * <p>Constructor for AbsoluteGraphicsVO.</p>
	 */
	private @SuppressWarnings("unused") AbsoluteGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	/**
	 * <p>Constructor for AbsoluteGraphicsVO.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
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
