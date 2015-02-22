package de.markusrother.pned.gui.actions;

import java.awt.Point;

/**
 * <p>
 * A simple provider of coordinates.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface LocationProvider {

    /**
     * <p>
     * Returns a coordinate.
     * </p>
     *
     * @return a {@link java.awt.Point} - the requested coordinate.
     */
    Point getLocation();

}
