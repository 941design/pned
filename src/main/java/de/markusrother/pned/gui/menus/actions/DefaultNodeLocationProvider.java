package de.markusrother.pned.gui.menus.actions;

import java.awt.Point;

/**
 * <p>DefaultNodeLocationProvider class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class DefaultNodeLocationProvider
	implements
		LocationProvider {

	/** Constant <code>defaultNodeLocation</code> */
	private static final Point defaultNodeLocation = new Point(100, 100);

	/** Constant <code>INSTANCE</code> */
	public static LocationProvider INSTANCE = new DefaultNodeLocationProvider();

	/**
	 * <p>Constructor for DefaultNodeLocationProvider.</p>
	 */
	private DefaultNodeLocationProvider() {
	}

	/** {@inheritDoc} */
	@Override
	public Point getLocation() {
		return defaultNodeLocation;
	}

}
