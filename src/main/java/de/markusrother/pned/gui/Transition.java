package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.TrigUtils.modPi;
import static java.lang.Math.PI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JTextField;

/**
 * This component should neither know its location nor its context!
 *
 */
class Transition extends AbstractNode {

	private static final Color nodeColorBG = new Color(120, 120, 120, 120);

	private final Dimension dimension;
	private final JTextField label;

	public Transition(final String label, final Dimension dimension) {
		super();
		// TODO - use model instead of label
		this.dimension = dimension;
		this.label = new JTextField(label);
		// addMouseListener(this);
		// addMouseMotionListener(this);
		add(this.label);
	}

	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		// TODO - how to manage node locations?
		// setPreferredSize(dimension);
		setForeground(nodeColorBG);
		g2.fill(getShape());
	}

	@Override
	Shape getShape() {
		return getRectangle();
	}

	private Rectangle getRectangle() {
		return new Rectangle(0, 0, dimension.width, dimension.height);
	}

	@Override
	public Point2D getIntersectionWithBounds(final double theta) {
		final double t = modPi(theta);
		// TODO - Assumes that rectangle origin is 0,0!
		final Rectangle r = getRectangle();
		final double absTheta = Math.abs(t);
		if (absTheta <= PI * 0.25) {
			// theta intersects with right vertical.
			return new Point2D.Double(r.width, r.height / 2.0 * (1 + Math.tan(theta)));
		} else if (absTheta >= PI * 0.75) {
			// theta intersects with left vertical.
			return new Point2D.Double(0, r.height / 2.0 * (1 - Math.tan(theta)));
		} else if (t > 0) {
			// theta intersects with top horizontal.
			return new Point2D.Double(r.width / 2.0 * (1 - Math.cos(theta + PI)), r.height);
		} else if (t < 0) {
			// theta intersects with bottom horizontal.
			return new Point2D.Double(r.width / 2.0 * (1 - Math.cos(theta + PI)), 0);
		} else {
			throw new IllegalStateException();
		}
	}
}
