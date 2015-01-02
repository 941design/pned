package de.markusrother.pned.gui.layout.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 * <p>MarkingStyle class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingStyle {

	/** Constant <code>DEFAULT</code> */
	public static final MarkingStyle DEFAULT;
	static {
		DEFAULT = new MarkingStyle();
		DEFAULT.setColor(Color.BLACK);
		DEFAULT.setShape(new Ellipse2D.Double(0, 0, 1, 1));
		DEFAULT.setSize(10);
		DEFAULT.setFontName(Font.SANS_SERIF);
		DEFAULT.setFontStyle(Font.BOLD);
	}

	private int size;
	private Shape shape;
	private Color color;
	private String fontName;
	private int fontStyle;

	/**
	 * <p>Getter for the field <code>size</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <p>Setter for the field <code>size</code>.</p>
	 *
	 * @param size a int.
	 */
	public void setSize(final int size) {
		this.size = size;
	}

	/**
	 * <p>Getter for the field <code>shape</code>.</p>
	 *
	 * @return a {@link java.awt.Shape} object.
	 */
	public Shape getShape() {
		final AffineTransform transform = AffineTransform.getScaleInstance(size, size);
		return transform.createTransformedShape(shape);
	}

	/**
	 * <p>Setter for the field <code>shape</code>.</p>
	 *
	 * @param shape a {@link java.awt.Shape} object.
	 */
	public void setShape(final Shape shape) {
		this.shape = shape;
	}

	/**
	 * <p>Getter for the field <code>color</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * <p>Setter for the field <code>color</code>.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	public void setColor(final Color color) {
		this.color = color;
	}

	/**
	 * <p>Getter for the field <code>fontName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * <p>Setter for the field <code>fontName</code>.</p>
	 *
	 * @param fontName a {@link java.lang.String} object.
	 */
	public void setFontName(final String fontName) {
		this.fontName = fontName;
	}

	/**
	 * <p>Getter for the field <code>fontStyle</code>.</p>
	 *
	 * @return a int.
	 */
	public int getFontStyle() {
		return fontStyle;
	}

	/**
	 * <p>Setter for the field <code>fontStyle</code>.</p>
	 *
	 * @param fontStyle a int.
	 */
	public void setFontStyle(final int fontStyle) {
		this.fontStyle = fontStyle;
	}

	/**
	 * <p>getFont.</p>
	 *
	 * @return a {@link java.awt.Font} object.
	 */
	public Font getFont() {
		return new Font(fontName, fontStyle, (int) (size / 1.2 + 0.5));
	}

}
