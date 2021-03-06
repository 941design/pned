package de.markusrother.pned.gui.components;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.gui.components.listeners.MarkingEditor;
import de.markusrother.pned.gui.core.NodeStyle;
import de.markusrother.pned.gui.core.model.MarkingStyleModel;

public class PlaceTest extends AbstractNodeTest<PlaceComponent> {

    private static final String NO_ID = null;

    private PlaceComponent place;

    private MarkingComponent createMarking() {
        return new MarkingComponent(eventMulticastMock, //
                Mockito.mock(MarkingStyleModel.class));
    }

    private void createPlace(final int extent) {
        final NodeStyle style = NodeStyle.newDefault();
        style.setSize(extent);
        place = new PlaceComponent(eventMulticastMock, //
                NO_ID, //
                createMarking(), //
                Mockito.mock(MarkingEditor.class), //
                style);
    }

    private void assertAngleIntersectsAt(final double theta, final Point2D expected) {
        super.assertAngleIntersectsAt(place, theta, expected);
    }

    @Test
    public void testDimensions() {
        final int extent = 100;
        createPlace(extent);
        assertEquals(extent, place.getPreferredSize().width);
        assertEquals(extent, place.getPreferredSize().height);
    }

    @Test
    public void testShape() {
        final int extent = 100;
        createPlace(extent);
        assertEquals(new Ellipse2D.Double(0, 0, 100, 100), place.getShape());
    }

    @Test
    public void testGetIntersectionWithBounds() {
        final int extent = 100;
        createPlace(extent);
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
    protected PlaceComponent getComponent() {
        return new PlaceComponent(eventMulticastMock, //
                NO_ID, //
                createMarking(), //
                Mockito.mock(MarkingEditor.class), //
                NodeStyle.newDefault());
    }

}
