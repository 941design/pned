package de.markusrother.util;

import static java.lang.Math.PI;

import java.awt.Point;

/**
 * <p>TrigUtils class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TrigUtils {

	/** Constant <code>TWO_PI=2 * Math.PI</code> */
	public static final double TWO_PI = 2 * Math.PI;

	/**
	 * <p>modPi.</p>
	 *
	 * @param theta a double.
	 * @return a double.
	 */
	public static double modPi(final double theta) {
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
	 * @param source a {@link java.awt.Point} object.
	 * @param target a {@link java.awt.Point} object.
	 * @return a double.
	 */
	public static double getRadiansOfDelta(final Point source, final Point target) {
		final double x = target.getX() - source.getX();
		final double y = target.getY() - source.getY();
		return Math.atan2(y, x);
	}

}
