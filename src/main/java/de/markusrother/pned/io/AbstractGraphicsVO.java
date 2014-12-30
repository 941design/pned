package de.markusrother.pned.io;

import java.awt.Point;

/**
 * <p>Abstract AbstractGraphicsVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractGraphicsVO {

	protected final LocationVO location;

	/**
	 * <p>Constructor for AbstractGraphicsVO.</p>
	 */
	protected @SuppressWarnings("unused") AbstractGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.location = null;
	}

	/**
	 * <p>Constructor for AbstractGraphicsVO.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 */
	public AbstractGraphicsVO(final Point point) {
		this.location = new LocationVO(point);
	}

	/**
	 * <p>Getter for the field <code>location</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.io.LocationVO} object.
	 */
	abstract LocationVO getLocation();
}
