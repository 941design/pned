package de.markusrother.pned.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.markusrother.swing.DragListener;
import de.markusrother.swing.HoverListener;

/**
 * 
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

	Point source;
	Point target;
	Component sourceComponent;
	Component targetComponent;

	private Polygon tip;
	private Line2D line;
	private Color fgColor;

	public EdgeComponent(final Component sourceComponent, final Point source, final Point target) {
		this.sourceComponent = sourceComponent;
		this.source = source;
		this.target = target;
		this.fgColor = standardColor;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = formatGraphics(g);
		line = new Line2D.Double(source, target);
		tip = createTip(getRadiansOfDelta(source, target));
		tip.translate(target.x, target.y);
		g2.draw(line);
		g2.fillPolygon(tip);
		// TODO - currently the component is as large as the entire grid!
		// System.out.println(getBounds());
	}

	private Graphics2D formatGraphics(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		setForeground(fgColor);
		return g2;
	}

	public Class<?> getSourceType() {
		return sourceComponent.getClass();
	}

	public Class<?> getTargetType() {
		return targetComponent.getClass();
	}

	public boolean hasTargetComponent() {
		return targetComponent != null;
	}

	private void setSource(final Point source) {
		this.source = source;
		repaint();
	}

	public void setTarget(final Point target) {
		this.target = target;
		repaint();
	}

	private void setSourceComponent(final Component sourceComponent) {
		this.sourceComponent = sourceComponent;
	}

	public void setTargetComponent(final Component targetComponent) {
		this.targetComponent = targetComponent;
	}

	public void finishedDrawing() {
		DragListener.addToComponent(sourceComponent, new DragListener() {
			@Override
			public void onDrag(final int deltaX, final int deltaY) {
				source.translate(deltaX, deltaY);
				repaint();
			}
		});
		DragListener.addToComponent(targetComponent, new DragListener() {
			@Override
			public void onDrag(final int deltaX, final int deltaY) {
				target.translate(deltaX, deltaY);
				repaint();
			}
		});
		HoverListener.addToComponent(this, new HoverListener() {

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
		});
	}

	boolean edgeContains(final Point point) {
		// WTF !? line.contains(point) returns false, with the explanation:
		// "lines never contain AREAS" WTF! A point is not an area...
		// TODO - line thickness is variable!
		return line != null && (line.ptSegDistSq(point) < 5 || tip.contains(point));
	}

}
