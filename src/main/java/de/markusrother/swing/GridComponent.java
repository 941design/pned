package de.markusrother.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * <p>
 * A simple grid component, with a background grid.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class GridComponent extends JComponent {

    private final Color gridColor;
    private final Dimension cellDimension;

    /**
     * <p>
     * Constructor for SnapGridComponent.
     * </p>
     *
     * @param cellDimension
     *            a {@link java.awt.Dimension} object.
     * @param gridColor
     *            a {@link java.awt.Color} object.
     */
    public GridComponent(final Dimension cellDimension, final Color gridColor) {
        this.cellDimension = cellDimension;
        this.gridColor = gridColor;
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        // TODO - Overridden, because ...
        super.paintComponent(g);
        g.setColor(gridColor);
        final int height = this.getHeight();
        final int width = this.getWidth();

        // Drawing vertical lines:
        // TODO - offset for actual line width!
        assert (cellDimension.width > 0);
        for (int x = cellDimension.width; x <= width; x = x + cellDimension.width) {
            // TODO - different line styles!
            g.drawLine(x, 0, x, height);
        }

        // Drawing horizontal lines:
        // TODO - offset for actual line height!
        assert (cellDimension.height > 0);
        for (int y = cellDimension.height; y <= height; y = y + cellDimension.height) {
            // TODO - different line styles!
            g.drawLine(0, y, width, y);
        }
    }

}
