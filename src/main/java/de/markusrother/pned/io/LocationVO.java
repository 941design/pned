package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * <p>LocationVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LocationVO {

	private final int x;
	private final int y;

	/**
	 * <p>Constructor for LocationVO.</p>
	 */
	private @SuppressWarnings("unused") LocationVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.x = -1;
		this.y = -1;
	}

	/**
	 * <p>Constructor for LocationVO.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 */
	public LocationVO(final Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	/**
	 * <p>Getter for the field <code>x</code>.</p>
	 *
	 * @return a int.
	 */
	@XmlAttribute
	public int getX() {
		return x;
	}

	/**
	 * <p>Getter for the field <code>y</code>.</p>
	 *
	 * @return a int.
	 */
	@XmlAttribute
	public int getY() {
		return y;
	}

}
