package de.markusrother.pned.gui.components;

import java.awt.geom.Point2D;

/**
 * Interface for Components with a shape.
 *
 * @author Markus Rother
 * @version 1.0
 */
interface Bounded {

    /**
     * Returns boundary point of this object at a given angle.
     *
     * @param theta
     *            growing clockwise
     * @return A Point on the boundary.
     */
    Point2D getBoundaryPoint(final double theta);
}
