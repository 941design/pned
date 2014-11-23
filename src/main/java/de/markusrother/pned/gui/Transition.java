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

import javax.swing.border.LineBorder;

/**
 *
 */
class Transition extends AbstractNode {

	private static final Color nodeColorBG = new Color(120, 120, 120, 120);

	private final Dimension dimension;

	public Transition(final Dimension dimension) {
		super();
		// TODO - use model!
		this.dimension = dimension;
		setOpaque(false);
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
	protected Shape getShape() {
		return getRectangle();
	}

	private Rectangle getRectangle() {
		return new Rectangle(0, 0, dimension.width, dimension.height);
	}

	@Override
	protected void setLayout(final State state) {
		switch (state) {
		case DEFAULT:
			setBorder(null);
			break;
		case HOVER:
			setBorder(new LineBorder(Color.GREEN));
			break;
		default:
			break;
		}
		repaint();
	}

	@Override
	public Point2D getBoundaryPoint(final double theta) {
		final double t = modPi(theta); // -PI <= t <= PI
		// TODO - Assumes that rectangle origin is 0,0!
		final Rectangle r = getRectangle();
		final double absTheta = Math.abs(t);
		if (absTheta <= PI * 0.25) {
			// theta intersects with right vertical.
			return new Point2D.Double(r.width, r.height * (1 + Math.tan(theta)) / 2.0);
		} else if (absTheta >= PI * 0.75) {
			// theta intersects with left vertical.
			return new Point2D.Double(0, r.height * (1 - Math.tan(theta)) / 2.0);
		} else if (t > 0) {
			// theta intersects with top horizontal.
			return new Point2D.Double(r.width * (1 - Math.cos(theta + PI)) / 2.0, r.height);
		} else if (t < 0) {
			// theta intersects with bottom horizontal.
			return new Point2D.Double(r.width * (1 - Math.cos(theta + PI)) / 2.0, 0);
		} else {
			throw new IllegalStateException();
		}
	}

}
