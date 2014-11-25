package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.util.TrigUtils.getRadiansOfDelta;

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

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.swing.DragDropAdapter;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

/**
 * TODO - manage the contact-points of source component, too. Create a segment
 * which is invisible, but connected, to the source components center, avoiding
 * flickering.
 */
public class EdgeComponent extends AbstractEdgeComponent<AbstractNode, AbstractNode>
	implements
		NodeListener,
		EdgeEditListener,
		Disposable {

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

	private static HoverListener createHoverListener(final EdgeComponent edge) {

		// TODO - extract class!

		return new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return edge.edgeContains(p);
			}

			@Override
			protected void startHover(final Component component) {
				edge.setColor(hoverColor);
			}

			@Override
			protected void endHover(final Component component) {
				edge.highlightStandard();
			}
		};
	}

	private static DragDropListener createDragDropListener(final EdgeComponent edge) {

		// TODO - extract class!

		return new DragDropAdapter() {

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				edge.connectToSource();
				edge.connectToTarget();
				edge.repaint();
			}
		};
	}

	private Polygon tip;
	private Line2D line;
	private Color fgColor;

	public EdgeComponent(final AbstractNode sourceComponent, final Point source, final Point target) {
		super(sourceComponent, source, target);
		this.fgColor = standardColor;
		eventBus.addNodeListener(this);
		eventBus.addEdgeEditListener(this);
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
		// TODO - Currently the edge component is as large as the entire grid!
		// We could optimize a little, here.
	}

	private Graphics2D formatGraphics(final Graphics g) {
		// TODO - apply state/styles.
		final Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		setForeground(fgColor);
		return g2;
	}

	public boolean acceptsTarget(final Component component) {
		return component instanceof AbstractNode //
				&& sourceComponent.getClass() != component.getClass();
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
	public void createPlace(final PlaceCreationRequest e) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public void createTransition(final TransitionCreationRequest e) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		final AbstractNode node = e.getNode();
		if (node.equals(sourceComponent) || node.equals(targetComponent)) {
			dispose();
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (acceptsTarget(e.getComponent())) {
			setTargetComponent((AbstractNode) e.getComponent());
			highlightValid();
		} else {
			highlightInvalid();
		}
	}

	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		removeTargetComponent();
		highlightStandard();
	}

	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (sourceComponent.equals(e.getComponent())) {
			// IGNORE - Just moving around on source component.
			return;
		}
		if (hasTargetComponent() && targetComponent.equals(e.getComponent())) {
			// IGNORE - Just moving around on target component.
			return;
		}
		// e.component() may or may not be a valid target. We are not interested
		// on that. This method is not responsible for connecting to targets!
		// That is done when entering potential target components. Also, we rely
		// on target removal on exiting components. Therefore, we can safely
		// connect to the point, without being bound to any target component.
		setUnboundTarget(e.getLocation());
		repaint();
	}

	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		dispose();
	}

	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (!acceptsTarget(e.getComponent())) {
			throw new IllegalArgumentException();
		}

		setTargetComponent((AbstractNode) e.getComponent());

		// TODO - Move to super (can then be used for other edges, too)
		final DragDropListener dragListener = createDragDropListener(this);
		// TODO - pass responsibility to node, in order to not leak listeners.
		// Components would then have to implement an interface to add
		// DragDropListener.
		DragDropListener.addToComponent(sourceComponent, dragListener);
		DragDropListener.addToComponent(targetComponent, dragListener);

		final HoverListener hoverListener = createHoverListener(this);
		// TODO - setHoverListener() -> to field.
		HoverListener.addToComponent(this, hoverListener);

		highlightStandard();
	}

	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// IGNORE - If we weren't started we would not exist...
	}

	@Override
	public void dispose() {
		// TODO - create generic removeAllListeners()
		eventBus.removeNodeListener(this);
		eventBus.removeEdgeEditListener(this);
		// TODO - may require synchronization, if two removal events are fired
		// before this is properly removed from eventBus.
		getParent().remove(this);
	}

}
