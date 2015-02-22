package de.markusrother.pned.gui.core;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import de.markusrother.pned.gui.core.model.NodeStyleModel;

/**
 * <p>
 * Default implementation of
 * {@link de.markusrother.pned.gui.core.model.NodeStyleModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeStyle extends AbstractStyle
    implements
        NodeStyleModel {

    /**
     * <p>
     * Returns new instance of a default
     * {@link de.markusrother.pned.gui.core.model.NodeStyleModel}.
     * </p>
     *
     * @return a {@link de.markusrother.pned.gui.core.NodeStyle} - the default
     *         style.
     */
    public static NodeStyle newDefault() {
        final NodeStyle style = new NodeStyle();
        style.setSize(40);
        style.setDefaultColor(new Color(120, 120, 120, 120));
        style.setHoverColor(new Color(160, 180, 220, 120));
        style.setSelectionColor(new Color(160, 160, 160, 120));
        style.setDefaultBorder(null);
        style.setSelectionBorder(new LineBorder(Color.MAGENTA, 2));
        style.setHoverBorder(new LineBorder(Color.BLUE, 2));
        return style;
    }

    private int size;
    private Color defaultColor;
    private Color selectionColor;
    private Color hoverColor;
    private Border defaultBorder;
    private Border selectionBorder;
    private Border hoverBorder;

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
    public Color getDefaultColor() {
        return defaultColor;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultColor(final Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    /** {@inheritDoc} */
    @Override
    public Color getSelectionColor() {
        return selectionColor;
    }

    /** {@inheritDoc} */
    @Override
    public void setSelectionColor(final Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    /** {@inheritDoc} */
    @Override
    public Color getHoverColor() {
        return hoverColor;
    }

    /** {@inheritDoc} */
    @Override
    public void setHoverColor(final Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    /** {@inheritDoc} */
    @Override
    public Border getDefaultBorder() {
        return defaultBorder;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultBorder(final Border defaultBorder) {
        this.defaultBorder = defaultBorder;
    }

    /** {@inheritDoc} */
    @Override
    public Border getSelectionBorder() {
        return selectionBorder;
    }

    /** {@inheritDoc} */
    @Override
    public void setSelectionBorder(final Border selectionBorder) {
        this.selectionBorder = selectionBorder;
    }

    /** {@inheritDoc} */
    @Override
    public Border getHoverBorder() {
        return hoverBorder;
    }

    /** {@inheritDoc} */
    @Override
    public void setHoverBorder(final Border hoverBorder) {
        this.hoverBorder = hoverBorder;
    }

    /** {@inheritDoc} */
    @Override
    public Color getValidColor() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void setValidColor(final Color color) {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public Color getInvalidColor() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void setInvalidColor(final Color color) {
        throw new UnsupportedOperationException();
    }

}
