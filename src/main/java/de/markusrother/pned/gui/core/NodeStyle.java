package de.markusrother.pned.gui.core;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import de.markusrother.pned.gui.core.model.NodeStyleModel;

/**
 * <p>
 * NodeStyle class.
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
	 * newDefault.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.NodeStyle} object.
	 */
	public static NodeStyle newDefault() {
		final NodeStyle style = new NodeStyle();
		style.setSize(40);
		style.setDefaultColor(new Color(120, 120, 120, 120));
		style.setHoverColor(new Color(160, 220, 180, 120));
		style.setSelectionColor(new Color(160, 160, 160, 120));
		style.setDefaultBorder(null);
		style.setSelectionBorder(new LineBorder(Color.MAGENTA, 2));
		style.setHoverBorder(new LineBorder(Color.GREEN, 2));
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

	@Override
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	/** {@inheritDoc} */
	@Override
	public Color getSelectionColor() {
		return selectionColor;
	}

	@Override
	public void setSelectionColor(final Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	/** {@inheritDoc} */
	@Override
	public Color getHoverColor() {
		return hoverColor;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverColor</code>.
	 * </p>
	 *
	 * @param hoverColor
	 *            a {@link java.awt.Color} object.
	 */
	@Override
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	/** {@inheritDoc} */
	@Override
	public Border getDefaultBorder() {
		return defaultBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultBorder</code>.
	 * </p>
	 *
	 * @param defaultBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setDefaultBorder(final Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	/** {@inheritDoc} */
	@Override
	public Border getSelectionBorder() {
		return selectionBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>selectionBorder</code>.
	 * </p>
	 *
	 * @param selectionBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setSelectionBorder(final Border selectionBorder) {
		this.selectionBorder = selectionBorder;
	}

	/** {@inheritDoc} */
	@Override
	public Border getHoverBorder() {
		return hoverBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverBorder</code>.
	 * </p>
	 *
	 * @param hoverBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setHoverBorder(final Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

	@Override
	public Color getValidColor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValidColor(final Color color) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Color getInvalidColor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInvalidColor(final Color color) {
		throw new UnsupportedOperationException();
	}

}
