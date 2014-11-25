package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.TrigUtilsTest.precision;
import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import de.markusrother.pned.gui.components.AbstractNode;

public class AbstractNodeTest {

	protected void assertAngleIntersectsAt(final AbstractNode node, final double theta, final Point2D expected) {
		final Point2D actual = node.getBoundaryPoint(theta);
		assertEquals(expected.getX(), actual.getX(), precision);
		assertEquals(expected.getY(), actual.getY(), precision);
	}

}