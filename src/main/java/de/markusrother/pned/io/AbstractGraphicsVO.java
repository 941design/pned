package de.markusrother.pned.io;

import java.awt.Point;

/**
 * <p>
 * Abstract superclass for value objects representing the graphics pnml tag.
 * Used for marshalling to pnml (xml).
 * </p>
 * 
 * <pre>
 * ...
 * &lt;graphics&gt;
 * 	&lt;offset x="23" y="42"/&gt;
 * &lt;/graphics&gt;
 * ...
 * </pre>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 * @see AbsolutePositionMarshaller
 */
public abstract class AbstractGraphicsVO {

	/**
	 * The graphic's coordinates.
	 */
	protected final LocationVO location;

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	protected AbstractGraphicsVO() {
		// IGNORE
		this.location = null;
	}

	/**
	 * <p>
	 * Constructor for AbstractGraphicsVO.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} representing the graphic's
	 *            coordinates.
	 */
	public AbstractGraphicsVO(final Point point) {
		this.location = new LocationVO(point);
	}

	/**
	 * <p>
	 * Getter for the field <code>location</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.io.LocationVO} value object
	 *         representing the graphic's coordinates.
	 */
	abstract LocationVO getLocation();
}
