package de.markusrother.pned.gui;

import static java.lang.Math.PI;

import java.awt.Point;

public class TrigUtils {

	public static final double TWO_PI = 2 * Math.PI;

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
	 * @param source
	 * @param target
	 * @return
	 */
	public static double getRadiansOfDelta(final Point source, final Point target) {
		final double x = target.getX() - source.getX();
		final double y = target.getY() - source.getY();
		return Math.atan2(y, x);
	}

}
