package de.markusrother.pned.gui.layout.style;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import de.markusrother.pned.gui.model.EdgeStyleModel;

/**
 * <p>
 * EdgeStyle class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeStyle extends AbstractStyle
	implements
		EdgeStyleModel {

	/** Constant <code>DEFAULT_TIP</code> */
	public static final Polygon DEFAULT_TIP;
	static {
		final Point2D[] points = new Point2D[] { //
		new Point2D.Double(0, 1), //
				new Point2D.Double(0, -1), //
				new Point2D.Double(-15, -7), //
				new Point2D.Double(-15, 7) };
		DEFAULT_TIP = new Polygon();
		for (final Point2D point : points) {
			DEFAULT_TIP.addPoint((int) point.getX(), (int) point.getY());
		}
	}

	public static EdgeStyleModel newDefault() {
		final EdgeStyleModel style = new EdgeStyle();
		style.setShape(DEFAULT_TIP);
		style.setSize(10);
		style.setStroke(new BasicStroke(2));
		style.setDefaultColor(Color.BLACK);
		style.setHoverColor(Color.BLUE);
		style.setValidColor(Color.GREEN);
		style.setInvalidColor(Color.RED);
		return style;
	}

	private Color defaultColor;
	private Color validColor;
	private Color invalidColor;
	private Color hoverColor;
	private Stroke lineStroke;
	private Shape tip;
	private int tipSize;

	@Override
	public Color getDefaultColor() {
		return defaultColor;
	}

	@Override
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
		fireChangeEvent();
	}

	@Override
	public Color getHoverColor() {
		return hoverColor;
	}

	@Override
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
		fireChangeEvent();
	}

	@Override
	public Color getValidColor() {
		return validColor;
	}

	@Override
	public void setValidColor(final Color validColor) {
		this.validColor = validColor;
		fireChangeEvent();
	}

	@Override
	public Color getInvalidColor() {
		return invalidColor;
	}

	@Override
	public void setInvalidColor(final Color invalidColor) {
		this.invalidColor = invalidColor;
		fireChangeEvent();
	}

	@Override
	public Stroke getStroke() {
		return lineStroke;
	}

	@Override
	public void setStroke(final Stroke lineStroke) {
		this.lineStroke = lineStroke;
		fireChangeEvent();
	}

	@Override
	public int getSize() {
		return tipSize;
	}

	@Override
	public void setSize(final int size) {
		this.tipSize = size;
		fireChangeEvent();
	}

	@Override
	public Shape getShape() {
		final double scaleFactor = tipSize / 10.0;
		final AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
		return transform.createTransformedShape(tip);
	}

	@Override
	public void setShape(final Shape shape) {
		tip = shape;
		fireChangeEvent();
	}

}
