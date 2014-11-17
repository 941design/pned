package de.markusrother.pned.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.markusrother.swing.snap.HoverListener;
import de.markusrother.swing.snap.SnapPoint;
import de.markusrother.swing.snap.SnapPointListener;

/**
 * FIXME - use HoverListener!
 */
class EdgeComponent extends JComponent {

	private static final Color standardColor = Color.BLACK;
	private static final Color hoverColor = Color.RED;
	private static final Stroke stroke = new BasicStroke(2);

	private static double getRadiansOfDelta(final Point source, final Point target) {
		final double x = target.getX() - source.getX();
		final double y = target.getY() - source.getY();
		return Math.atan2(y, x);
	}

	// TODO - allow different tip shapes
	private static Polygon createTip(final double angle) {
		final Point2D[] points = new Point2D[] { //
		new Point2D.Double(0, 1), //
				new Point2D.Double(0, -1), //
				new Point2D.Double(-15, -7), //
				new Point2D.Double(-15, 7) };
		// TODO - pass the transformer to a tip instance
		AffineTransform.getRotateInstance(angle).transform(points, 0, points, 0, 4);
		return createPolygon(points);
	}

	private static Polygon createPolygon(final Point2D[] points) {
		final Polygon polygon = new Polygon();
		for (final Point2D point : points) {
			polygon.addPoint((int) point.getX(), (int) point.getY());
		}
		return polygon;
	}

	private final SnapPoint source;
	private SnapPoint target;
	private Polygon tip;
	private Line2D line;
	private Color fgColor;

	public EdgeComponent(final SnapPoint source) {
		this.source = source;
		this.source.addSnapPointListener(new SnapPointListener() {
			@Override
			public void snapPointMoved(final int deltaX, final int deltaY) {
				source.translate(deltaX, deltaY);
				repaint();
			}
		});
		this.fgColor = standardColor;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = format(g);
		line = new Line2D.Double(source, target);
		tip = createTip(getRadiansOfDelta(source, target));
		tip.translate(target.x, target.y);
		g2.draw(line);
		g2.fillPolygon(tip);
		// TODO - currently the component is as large as the entire grid!
		// System.out.println(getBounds());
	}

	private Graphics2D format(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		setForeground(fgColor);
		return g2;
	}

	public void setTarget(final SnapPoint target) {
		this.target = target;
	}

	public boolean targetIsNode() {
		return target.getTargetComponent() instanceof NodeComponent;
	}

	public void finishedDrawing() {
		source.setPermanentlyVisible(true);
		target.setPermanentlyVisible(true);
		final SnapPoint t = target;
		// TODO - create inner class to return SnapPointListener with reference
		// to this.
		target.addSnapPointListener(new SnapPointListener() {
			@Override
			public void snapPointMoved(final int deltaX, final int deltaY) {
				t.translate(deltaX, deltaY);
				repaint();
			}
		});
		addHoverListener();
	}

	private void addHoverListener() {
		final HoverListener hoverListener = new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return edgeContains(p);
			}

			@Override
			protected void startHover() {
				fgColor = hoverColor;
				repaint();
			}

			@Override
			protected void endHover() {
				fgColor = standardColor;
				repaint();
			}
		};
		addMouseListener(hoverListener);
		addMouseMotionListener(hoverListener);
	}

	public double length() {
		return target == null ? 0 : source.distance(target);
	}

	private boolean edgeContains(final Point point) {
		// WTF !? line.contains(point) returns false, with the explanation:
		// "lines never contain AREAS" WTF! A point is not an area...
		// TODO - line thickness is variable!
		return line != null && (line.ptSegDistSq(point) < 5 || tip.contains(point));
	}

}
