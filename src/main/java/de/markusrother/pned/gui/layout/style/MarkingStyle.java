package de.markusrother.pned.gui.layout.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import de.markusrother.pned.gui.model.MarkingStyleModel;

/**
 * <p>
 * MarkingStyle class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingStyle extends AbstractStyle
	implements
		MarkingStyleModel {

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

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(final int size) {
		this.size = size;
		fireChangeEvent();
	}

	@Override
	public Shape getShape() {
		final AffineTransform transform = AffineTransform.getScaleInstance(size, size);
		return transform.createTransformedShape(shape);
	}

	@Override
	public void setShape(final Shape shape) {
		this.shape = shape;
		fireChangeEvent();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(final Color color) {
		this.color = color;
		fireChangeEvent();
	}

	@Override
	public String getFontName() {
		return fontName;
	}

	@Override
	public void setFontName(final String fontName) {
		this.fontName = fontName;
		fireChangeEvent();
	}

	@Override
	public int getFontStyle() {
		return fontStyle;
	}

	@Override
	public void setFontStyle(final int fontStyle) {
		this.fontStyle = fontStyle;
		fireChangeEvent();
	}

	@Override
	public Font getFont() {
		return new Font(fontName, fontStyle, (int) (size / 1.2 + 0.5));
	}

}
