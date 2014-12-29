package de.markusrother.pned.gui.components;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.junit.Test;

public class PlaceTest extends AbstractNodeTest<Place> {

	private Place place;

	private void createPlace(final Dimension dimension) {
		place = new Place((int) dimension.getWidth());
	}

	private void assertAngleIntersectsAt(final double theta, final Point2D expected) {
		super.assertAngleIntersectsAt(place, theta, expected);
	}

	@Test
	public void testDimensions() {
		final Dimension dimension = new Dimension(100, 100);
		createPlace(dimension);
		assertEquals(dimension, place.getPreferredSize());
	}

	@Test
	public void testShape() {
		final Dimension dimension = new Dimension(100, 100);
		createPlace(dimension);
		assertEquals(new Ellipse2D.Double(0, 0, 100, 100), place.getShape());
	}

	@Test
	public void testGetIntersectionWithBounds() {
		final Dimension dimension = new Dimension(100, 100);
		createPlace(dimension);
		// 0 degrees
		assertAngleIntersectsAt(0, new Point(100, 50));
		// 180 degrees
		assertAngleIntersectsAt(Math.PI, new Point(0, 50));
		// -180 degrees
		assertAngleIntersectsAt(-Math.PI, new Point(0, 50));
		// 90 degrees
		assertAngleIntersectsAt(Math.PI / 2.0, new Point(50, 100));
		// -90 degrees
		assertAngleIntersectsAt(-Math.PI / 2.0, new Point(50, 0));
	}

	@Override
	protected Place getComponent() {
		return new Place(0);
	}
}
