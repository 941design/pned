package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.delta;
import static de.markusrother.pned.gui.TrigUtils.getRadiansOfDelta;

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
import de.markusrother.swing.snap.SnapGridComponent;

/**
 * 
 */
class EdgeComponent extends JComponent {

	private static final Color standardColor = Color.BLACK;
	private static final Color hoverColor = Color.BLUE;
	private static final Color validColor = Color.GREEN;
	private static final Color invalidColor = Color.RED;
	private static final Stroke stroke = new BasicStroke(2);

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
	AbstractNode sourceComponent;
	AbstractNode targetComponent;

	private Polygon tip;
	private Line2D line;
	private Color fgColor;

	public EdgeComponent(final AbstractNode sourceComponent, final Point source, final Point target) {
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

	private Point getGridRelativeLocation(final Point point) {
		// TODO - this is a somewhat dirty hack! I left in the cast to
		// illustrate the ugliness!
		return delta(point, ((SnapGridComponent) getParent()).getLocationOnScreen());
	}

	public Class<?> getSourceType() {
		return sourceComponent.getClass();
	}

	public Class<?> getTargetType() {
		return targetComponent.getClass();
	}

	public void setSource(final Point source) {
		this.source = source;
		repaint();
	}

	public void setTarget(final Point target) {
		this.target = target;
		repaint();
	}

	public AbstractNode getSourceComponent() {
		return sourceComponent;
	}

	private void setSourceComponent(final AbstractNode sourceComponent) {
		this.sourceComponent = sourceComponent;
	}

	public AbstractNode getTargetComponent() {
		return targetComponent;
	}

	public void setTargetComponent(final AbstractNode targetComponent) {
		this.targetComponent = targetComponent;
	}

	public boolean hasTargetComponent() {
		return targetComponent != null;
	}

	public void removeTargetComponent() {
		targetComponent = null;
	}

	public boolean acceptsTarget(final Component component) {
		return component instanceof AbstractNode //
				&& sourceComponent.getClass() != component.getClass();
	}

	public void finishedDrawing() {
		final DragListener dragListener = new DragListener() {
			@Override
			public void onDrag(final int deltaX, final int deltaY) {
				connectToSource(getSourceComponent());
				connectToTarget(getTargetComponent());
				repaint();
			}
		};
		DragListener.addToComponent(getSourceComponent(), dragListener);
		DragListener.addToComponent(getTargetComponent(), dragListener);
		HoverListener.addToComponent(this, new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return edgeContains(p);
			}

			@Override
			protected void startHover() {
				setColor(hoverColor);
			}

			@Override
			protected void endHover() {
				highlightStandard();
			}
		});
	}

	protected void setColor(final Color color) {
		fgColor = color;
		repaint();
	}

	boolean edgeContains(final Point point) {
		// WTF !? line.contains(point) returns false, with the explanation:
		// "lines never contain AREAS" WTF! A point is not an area...
		// TODO - line thickness is variable!
		return line != null && (line.ptSegDistSq(point) < 5 || tip.contains(point));
	}

	public void highlightValid() {
		setColor(validColor);
	}

	public void highlightInvalid() {
		setColor(invalidColor);
	}

	public void highlightStandard() {
		setColor(standardColor);
	}

	public double getAngle() {
		return getRadiansOfDelta(source, target);
	}

	private Point round(final Point2D point) {
		return new Point( //
				(int) Math.floor(point.getX() + 0.5), //
				(int) Math.floor(point.getY() + 0.5));
	}

	/**
	 * TODO - Currently requires component to be added to parent already, to
	 * retrieve position!
	 */
	public void connectToTarget(final AbstractNode node) {
		// TODO - make nicer
		final double angle = getAngle();
		final Point intersection = node.getLocationOnScreen();
		// TODO - must rotate because...
		final Point boundary = round(node.getIntersectionWithBounds(angle + Math.PI));
		intersection.translate(boundary.x, boundary.y);
		setTarget(getGridRelativeLocation(intersection));
	}

	/**
	 * TODO - Currently requires component to be added to parent already, to
	 * retrieve position!
	 */
	public void connectToSource(final AbstractNode node) {
		// TODO - make nicer
		final double angle = getAngle();
		final Point intersection = node.getLocationOnScreen();
		final Point boundary = round(node.getIntersectionWithBounds(angle));
		intersection.translate(boundary.x, boundary.y);
		setSource(getGridRelativeLocation(intersection));
	}

}
