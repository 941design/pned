package de.markusrother.pned.gui.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import de.markusrother.pned.gui.core.model.EdgeStyleModel;

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

	/**
	 * <p>
	 * newDefault.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel}
	 *         object.
	 */
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

	/** {@inheritDoc} */
	@Override
	public Color getDefaultColor() {
		return defaultColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
		fireChangeEvent();
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
		fireChangeEvent();
	}

	/** {@inheritDoc} */
	@Override
	public Color getValidColor() {
		return validColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setValidColor(final Color validColor) {
		this.validColor = validColor;
		fireChangeEvent();
	}

	/** {@inheritDoc} */
	@Override
	public Color getInvalidColor() {
		return invalidColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setInvalidColor(final Color invalidColor) {
		this.invalidColor = invalidColor;
		fireChangeEvent();
	}

	/** {@inheritDoc} */
	@Override
	public Stroke getStroke() {
		return lineStroke;
	}

	/** {@inheritDoc} */
	@Override
	public void setStroke(final Stroke lineStroke) {
		this.lineStroke = lineStroke;
		fireChangeEvent();
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		return tipSize;
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final int size) {
		this.tipSize = size;
		fireChangeEvent();
	}

	/** {@inheritDoc} */
	@Override
	public Shape getShape() {
		final double scaleFactor = tipSize / 10.0;
		final AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
		return transform.createTransformedShape(tip);
	}

	/** {@inheritDoc} */
	@Override
	public void setShape(final Shape shape) {
		tip = shape;
		fireChangeEvent();
	}

	@Override
	public Color getSelectionColor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelectionColor(final Color color) {
		throw new UnsupportedOperationException();
	}

}
