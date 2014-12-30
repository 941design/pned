package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>RelativeGraphicsVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RelativeGraphicsVO extends AbstractGraphicsVO {

	/**
	 * <p>Constructor for RelativeGraphicsVO.</p>
	 */
	private @SuppressWarnings("unused") RelativeGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	/**
	 * <p>Constructor for RelativeGraphicsVO.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 */
	public RelativeGraphicsVO(final Point point) {
		super(point);
	}

	/** {@inheritDoc} */
	@Override
	@XmlElement(name = "offset")
	public LocationVO getLocation() {
		return location;
	}

}
