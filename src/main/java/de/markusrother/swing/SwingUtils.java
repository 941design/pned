package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

public class SwingUtils {

	/**
	 * <p>
	 * getCenter.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point getCenter(final Component component) {
		final Point point = component.getLocation();
		point.translate( //
				(int) Math.floor((component.getWidth() + 0.5) / 2.0), //
				(int) Math.floor((component.getHeight() + 0.5) / 2.0));
		return point;
	}

}
