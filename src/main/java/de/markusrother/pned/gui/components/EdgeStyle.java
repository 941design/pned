package de.markusrother.pned.gui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * <p>EdgeStyle class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeStyle {

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

	/** Constant <code>DEFAULT</code> */
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

	/**
	 * <p>Getter for the field <code>defaultColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * <p>Setter for the field <code>defaultColor</code>.</p>
	 *
	 * @param defaultColor a {@link java.awt.Color} object.
	 */
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	/**
	 * <p>Getter for the field <code>hoverColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getHoverColor() {
		return hoverColor;
	}

	/**
	 * <p>Setter for the field <code>hoverColor</code>.</p>
	 *
	 * @param hoverColor a {@link java.awt.Color} object.
	 */
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	/**
	 * <p>Getter for the field <code>validColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getValidColor() {
		return validColor;
	}

	/**
	 * <p>Setter for the field <code>validColor</code>.</p>
	 *
	 * @param validColor a {@link java.awt.Color} object.
	 */
	public void setValidColor(final Color validColor) {
		this.validColor = validColor;
	}

	/**
	 * <p>Getter for the field <code>invalidColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getInvalidColor() {
		return invalidColor;
	}

	/**
	 * <p>Setter for the field <code>invalidColor</code>.</p>
	 *
	 * @param invalidColor a {@link java.awt.Color} object.
	 */
	public void setInvalidColor(final Color invalidColor) {
		this.invalidColor = invalidColor;
	}

	/**
	 * <p>Getter for the field <code>lineStroke</code>.</p>
	 *
	 * @return a {@link java.awt.Stroke} object.
	 */
	public Stroke getLineStroke() {
		return lineStroke;
	}

	/**
	 * <p>Setter for the field <code>lineStroke</code>.</p>
	 *
	 * @param lineStroke a {@link java.awt.Stroke} object.
	 */
	public void setLineStroke(final Stroke lineStroke) {
		this.lineStroke = lineStroke;
	}

	/**
	 * <p>Getter for the field <code>tipSize</code>.</p>
	 *
	 * @return a int.
	 */
	public int getTipSize() {
		return tipSize;
	}

	/**
	 * <p>Setter for the field <code>tipSize</code>.</p>
	 *
	 * @param tipSize a int.
	 */
	public void setTipSize(final int tipSize) {
		this.tipSize = tipSize;
	}

	/**
	 * <p>Getter for the field <code>tip</code>.</p>
	 *
	 * @return a {@link java.awt.Shape} object.
	 */
	public Shape getTip() {
		final double scaleFactor = tipSize / 10.0;
		final AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
		return transform.createTransformedShape(tip);
	}

	/**
	 * <p>Setter for the field <code>tip</code>.</p>
	 *
	 * @param tip a {@link java.awt.Shape} object.
	 */
	private void setTip(final Shape tip) {
		this.tip = tip;
	}
}
