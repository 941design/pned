package de.markusrother.pned.gui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class EdgeStyle {

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

	public static final EdgeStyle DEFAULT;
	static {
		DEFAULT = new EdgeStyle();
		DEFAULT.setTip(DEFAULT_TIP);
		DEFAULT.setTipSize(10);
		DEFAULT.setLineStroke(new BasicStroke(2));
		DEFAULT.setDefaultColor(Color.BLACK);
		DEFAULT.setHoverColor(Color.BLUE);
		DEFAULT.setValidColor(Color.GREEN);
		DEFAULT.setInvalidColor(Color.RED);
	}

	private Color defaultColor;
	private Color validColor;
	private Color invalidColor;
	private Color hoverColor;
	private Stroke lineStroke;
	private Shape tip;
	private int tipSize;

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public Color getValidColor() {
		return validColor;
	}

	public void setValidColor(final Color validColor) {
		this.validColor = validColor;
	}

	public Color getInvalidColor() {
		return invalidColor;
	}

	public void setInvalidColor(final Color invalidColor) {
		this.invalidColor = invalidColor;
	}

	public Stroke getLineStroke() {
		return lineStroke;
	}

	public void setLineStroke(final Stroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	public int getTipSize() {
		return tipSize;
	}

	public void setTipSize(final int tipSize) {
		this.tipSize = tipSize;
	}

	public Shape getTip() {
		final double scaleFactor = tipSize / 10.0;
		final AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
		return transform.createTransformedShape(tip);
	}

	private void setTip(final Shape tip) {
		this.tip = tip;
	}
}
