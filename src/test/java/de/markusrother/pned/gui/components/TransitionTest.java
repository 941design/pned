package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.TrigUtilsTest.precision;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import org.junit.Test;

import de.markusrother.pned.gui.core.NodeStyle;

public class TransitionTest extends AbstractNodeTest<TransitionComponent> {

    private static final String NO_ID = null;

    private TransitionComponent transition;

    private void createTransition(final int extent) {
        final NodeStyle style = NodeStyle.newDefault();
        style.setSize(extent);
        transition = new TransitionComponent(eventMulticastMock, //
                NO_ID, //
                style);
    }

    private void assertAngleIntersectsAt(final double theta, final Point2D expected) {
        super.assertAngleIntersectsAt(transition, theta, expected);
    }

    @Test
    public void testExtent() {
        final int extent = 100;
        createTransition(extent);
        assertEquals(extent, transition.getPreferredSize().height);
        assertEquals(extent, transition.getPreferredSize().width);
    }

    @Test
    public void testShape() {
        final int extent = 100;
        createTransition(extent);
        assertEquals(new Rectangle(0, 0, 100, 100), transition.getShape());
    }

    @Test
    public void testGetIntersectionWithBounds() {
        final int extent = 100;
        createTransition(extent);

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
    protected TransitionComponent getComponent() {
        return new TransitionComponent(eventMulticastMock, //
                NO_ID, //
                NodeStyle.newDefault());
    }
}
