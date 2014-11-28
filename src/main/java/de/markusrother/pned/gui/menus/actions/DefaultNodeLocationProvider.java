package de.markusrother.pned.gui.menus.actions;

import java.awt.Point;

public class DefaultNodeLocationProvider
	implements
		LocationProvider {

	private static final Point defaultNodeLocation = new Point(100, 100);

	public static LocationProvider INSTANCE = new DefaultNodeLocationProvider();

	private DefaultNodeLocationProvider() {
	}

	@Override
	public Point getLocation() {
		return defaultNodeLocation;
	}

}
