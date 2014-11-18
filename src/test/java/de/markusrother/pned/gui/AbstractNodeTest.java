package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.TrigUtilsTest.precision;
import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

public class AbstractNodeTest {

	protected void assertAngleIntersectsAt(final AbstractNode node, final double theta, final Point2D expected) {
		final Point2D actual = node.getIntersectionWithBounds(theta);
		assertEquals(expected.getX(), actual.getX(), precision);
		assertEquals(expected.getY(), actual.getY(), precision);
	}

}
