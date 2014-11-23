package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;
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

import de.markusrother.swing.DragDropAdapter;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

/**
 * 
 */
class EdgeComponent extends AbstractEdgeComponent<AbstractNode, AbstractNode> implements NodeListener, Disposable {

	private static final Color standardColor = Color.BLACK;
	private static final Color hoverColor = Color.BLUE;
	private static final Color validColor = Color.GREEN;
	private static final Color invalidColor = Color.RED;
	private static final Stroke stroke = new BasicStroke(2);

	// TODO - Extract class and interface to allow different tip shapes.
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

	private Polygon tip;
	private Line2D line;
	private Color fgColor;

	public EdgeComponent(final AbstractNode sourceComponent, final Point source, final Point target) {
		super(sourceComponent, source, target);
		this.fgColor = standardColor;
		eventBus.addNodeListener(this);
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

	public boolean acceptsTarget(final Component component) {
		return component instanceof AbstractNode //
				&& sourceComponent.getClass() != component.getClass();
	}

	public void finishedDrawing() {
		// TODO - Must retrieve components dynamically, in case they change (not
		// yet implemented).
		// TODO - Move to super (can then be used for other edges, too)
		final DragDropListener dragListener = new DragDropAdapter() {

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				connectToSource();
				connectToTarget();
				repaint();
			}

		};
		DragDropListener.addToComponent(getSourceComponent(), dragListener);
		DragDropListener.addToComponent(getTargetComponent(), dragListener);
		HoverListener.addToComponent(this, new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return edgeContains(p);
			}

			@Override
			protected void startHover(final Component component) {
				setColor(hoverColor);
			}

			@Override
			protected void endHover(final Component component) {
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

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		if (e.getNode() == sourceComponent || e.getNode() == targetComponent) {
			dispose();
		}
	}

	@Override
	public void dispose() {
		eventBus.removeNodeListener(this);
		// TODO - may require synchronization, if two removal events are fired
		// before this is properly removed from eventBus.
		getParent().remove(this);
	}

}
