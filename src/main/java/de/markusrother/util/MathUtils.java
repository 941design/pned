package de.markusrother.util;

import static java.lang.Math.PI;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * <p>
 * MathUtils class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MathUtils {

	/** Constant <code>TWO_PI=2 * Math.PI</code> */
	public static final double TWO_PI = 2 * Math.PI;

	/**
	 * <p>
	 * modPi.
	 * </p>
	 *
	 * @param theta
	 *            a double.
	 * @return a double.
	 */
	public static double moduloPi(final double theta) {
		final double t = theta % TWO_PI;
		if (Math.abs(t) <= PI) {
			return t;
		} else {
			return Math.signum(theta) * (t - TWO_PI);
		}
	}

	/**
	 * Returns an angle that 'opens' to the bottom.
	 *
	 * @param origin
	 *            a {@link java.awt.Point} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a double.
	 */
	public static double getRadiansOfDelta(final Point origin, final Point point) {
		final Point loc = point.getLocation();
		loc.translate(-origin.x, -origin.y);
		return getRadiansOfCoordinate(loc);
	}

	/**
	 * <p>
	 * getRadiansOfCoordinate.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a double.
	 */
	public static double getRadiansOfCoordinate(final Point point) {
		return Math.atan2(point.y, point.x);
	}

	/**
	 * <p>
	 * round.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.geom.Point2D} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point round(final Point2D point) {
		return new Point( //
				(int) Math.floor(point.getX() + 0.5), //
				(int) Math.floor(point.getY() + 0.5));
	}

}
