package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class MarkingStyle {

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

	public int getSize() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public Shape getShape() {
		final AffineTransform transform = AffineTransform.getScaleInstance(size, size);
		return transform.createTransformedShape(shape);
	}

	public void setShape(final Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(final String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(final int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public Font getFont() {
		return new Font(fontName, fontStyle, (int) (size / 1.2 + 0.5));
	}

}
