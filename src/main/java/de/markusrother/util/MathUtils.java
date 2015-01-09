package de.markusrother.util;

import static java.lang.Math.PI;

import java.awt.Point;

/**
 * <p>
 * TrigUtils class.
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

	public static double getRadiansOfCoordinate(final Point point) {
		return Math.atan2(point.y, point.x);
	}

}
