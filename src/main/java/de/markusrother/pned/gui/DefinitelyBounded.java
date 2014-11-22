package de.markusrother.pned.gui;

import java.awt.geom.Point2D;

public interface DefinitelyBounded {

	/**
	 * TODO - This could become a PathIterator to walk the entire boundary at a
	 * chosen precision. Think of a radar image, which detects the outside
	 * shape.
	 * 
	 * TODO - Name: contour, profile, silhouette, outline, radial ..
	 * 
	 * NOTE - If the shape is a polygon it may have cavities.
	 * 
	 * @param theta
	 *            growing clockwise
	 * @return A Point on the boundary of this.getShape().
	 */
	Point2D getIntersectionWithBounds(final double theta);
}
