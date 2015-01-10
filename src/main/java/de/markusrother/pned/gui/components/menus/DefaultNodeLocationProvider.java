package de.markusrother.pned.gui.components.menus;

import java.awt.Point;

import de.markusrother.pned.gui.actions.LocationProvider;

/**
 * <p>
 * Default, singleton implementation of
 * {@link de.markusrother.pned.gui.actions.LocationProvider}.
 * </p>
 * 
 * TODO - This should depend on the current viewport!
 *
 * @author Markus Rother
 * @version 1.0
 */
class DefaultNodeLocationProvider
	implements
		LocationProvider {

	/**
	 * Constant <code>defaultNodeLocation</code> at x=100, y=100.
	 */
	private static final Point defaultNodeLocation = new Point(100, 100);

	/**
	 * Constant <code>INSTANCE</code> - the singleton instance.
	 */
	public static LocationProvider INSTANCE = new DefaultNodeLocationProvider();

	/**
	 * <p>
	 * Constructor for DefaultNodeLocationProvider.
	 * </p>
	 */
	private DefaultNodeLocationProvider() {
	}

	/** {@inheritDoc} */
	@Override
	public Point getLocation() {
		return defaultNodeLocation;
	}

}
