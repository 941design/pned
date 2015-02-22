package de.markusrother.pned.gui.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import de.markusrother.pned.gui.core.model.MarkingStyleModel;

/**
 * <p>
 * Default implementation of
 * {@link de.markusrother.pned.gui.core.model.MarkingStyleModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingStyle extends AbstractStyle
    implements
        MarkingStyleModel {

    /** Constant <code>FONT_SIZE_RATIO=1.6</code> */
    private static final double FONT_SIZE_RATIO = 1.6;

    /**
     * <p>
     * Returns new instance of a default
     * {@link de.markusrother.pned.gui.core.model.MarkingStyleModel}.
     * </p>
     *
     * @return a {@link de.markusrother.pned.gui.core.model.MarkingStyleModel} -
     *         the default style.
     */
    public static MarkingStyleModel newDefault() {
        final MarkingStyleModel markingStyle = new MarkingStyle();
        markingStyle.setColor(Color.BLACK);
        markingStyle.setShape(new Ellipse2D.Double(0, 0, 1, 1));
        markingStyle.setSize(10);
        markingStyle.setFontName(Font.SANS_SERIF);
        markingStyle.setFontStyle(Font.BOLD);
        return markingStyle;
    }

    private int size;
    private Shape shape;
    private Color color;
    private String fontName;
    private int fontStyle;

    /** {@inheritDoc} */
    @Override
    public int getSize() {
        return size;
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final int size) {
        this.size = size;
        fireChangeEvent();
    }

    /** {@inheritDoc} */
    @Override
    public Shape getShape() {
        final AffineTransform transform = AffineTransform.getScaleInstance(size, size);
        return transform.createTransformedShape(shape);
    }

    /** {@inheritDoc} */
    @Override
    public void setShape(final Shape shape) {
        this.shape = shape;
        fireChangeEvent();
    }

    /** {@inheritDoc} */
    @Override
    public Color getColor() {
        return color;
    }

    /** {@inheritDoc} */
    @Override
    public void setColor(final Color color) {
        this.color = color;
        fireChangeEvent();
    }

    /** {@inheritDoc} */
    @Override
    public String getFontName() {
        return fontName;
    }

    /** {@inheritDoc} */
    @Override
    public void setFontName(final String fontName) {
        this.fontName = fontName;
        fireChangeEvent();
    }

    /** {@inheritDoc} */
    @Override
    public int getFontStyle() {
        return fontStyle;
    }

    /** {@inheritDoc} */
    @Override
    public void setFontStyle(final int fontStyle) {
        this.fontStyle = fontStyle;
        fireChangeEvent();
    }

    /** {@inheritDoc} */
    @Override
    public Font getFont() {
        return new Font(fontName, fontStyle, (int) (size * FONT_SIZE_RATIO));
    }

    /** {@inheritDoc} */
    @Override
    public Stroke getStroke() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void setStroke(final Stroke stroke) {
        throw new UnsupportedOperationException();
    }

}
