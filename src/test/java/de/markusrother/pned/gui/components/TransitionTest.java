package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.TrigUtilsTest.precision;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.junit.Test;

public class TransitionTest extends AbstractNodeTest<Transition> {

	private Transition transition;

	private void createTransition(final Dimension dimension) {
		transition = new Transition((int) dimension.getWidth());
	}

	private void assertAngleIntersectsAt(final double theta, final Point2D expected) {
		super.assertAngleIntersectsAt(transition, theta, expected);
	}

	@Test
	public void testDimensions() {
		final Dimension dimension = new Dimension(100, 100);
		createTransition(dimension);
		assertEquals(dimension, transition.getPreferredSize());
	}

	@Test
	public void testShape() {
		final Dimension dimension = new Dimension(100, 100);
		createTransition(dimension);
		assertEquals(new Rectangle(0, 0, 100, 100), transition.getShape());
	}

	@Test
	public void testGetIntersectionWithBounds() {
		final Dimension dimension = new Dimension(100, 100);
		createTransition(dimension);

		// Centers:
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

		// Corners:
		// 45 degrees
		assertAngleIntersectsAt(Math.PI * 0.25, new Point(100, 100));
		// -45 degrees
		assertAngleIntersectsAt(Math.PI * -0.25, new Point(100, 0));
		// 135 degrees
		assertAngleIntersectsAt(Math.PI * 0.75, new Point(0, 100));
		// -135 degrees
		assertAngleIntersectsAt(Math.PI * -0.75, new Point(0, 0));

		// Other:
		final Point2D actual = transition.getBoundaryPoint(Math.PI / 3);
		assertEquals(100, actual.getY(), precision);
		assertTrue(actual.getX() < 100);
		assertTrue(actual.getX() > 50);
	}

	@Override
	protected Transition getComponent() {
		return new Transition(0);
	}
}
