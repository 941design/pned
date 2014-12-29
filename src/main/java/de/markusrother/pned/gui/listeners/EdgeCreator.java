package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.COMPONENT_ENTERED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.COMPONENT_EXITED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_CANCELLED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_CHANGED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_FINISHED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_STARTED;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.pned.gui.components.PnGridPanel;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.EdgeEditEvent.Type;
import de.markusrother.swing.DoubleClickListener;

/**
 * TODO - This could be split into an initial listener for receiving the double
 * click and an EdgeDrawListener or EdgeEditor. That would avoid all the
 * duplicated edge != null checks.
 *
 */
public class EdgeCreator extends DoubleClickListener {

	// TODO - Drawing could also start upon exit!

	private final PnGridPanel pnGridPanel;

	private EdgeComponent edge;

	private final EventBus eventBus;

	public static void addToComponent(final Component component, final EdgeCreator listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public static void removeFromComponent(final Component component, final EdgeCreator listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	public EdgeCreator(final EventBus eventBus, final PnGridPanel pnGridPanel) {
		this.eventBus = eventBus;
		this.pnGridPanel = pnGridPanel;
	}

	private AbstractNode expectNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private Point getParentRelativeLocation(final MouseEvent e) {
		final Point point = e.getPoint();
		final Component eventComponent = e.getComponent();
		final Container sourceComponentsParent = edge.getSourceComponent().getParent();
		if (eventComponent == sourceComponentsParent) {
			// This is the point we are interested in. The edge layer should
			// have the same bounds as the source components' parent. TODO -
			// doc!
			return point;
		} else if (eventComponent.getParent() == sourceComponentsParent) {
			// We can easily translate the point, knowing that the source
			// component's parent has the same bounds as the edge layer.
			point.translate(eventComponent.getX(), eventComponent.getY());
			return point;
		} else if (eventComponent.getParent() == edge.getParent()) {
			// TODO - Should not happen! Why does edge need this listener!
			throw new RuntimeException("TODO");
		} else {
			// A listener was registered at a component, whose parent relative
			// location cannot be retrieved. TODO - Add this restriction to doc.
			// TODO
			throw new IllegalStateException("must have same parent / be on same layer");
		}
	}

	private void fire(final Type type, final MouseEvent e, final Component component) {
		final Point location = getParentRelativeLocation(e);
		fire(new EdgeEditEvent(type, this, edge, location, component));
	}

	private void fire(final EdgeEditEvent e) {
		eventBus.fireEdgeEditEvent(e);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
		if (edge == null) {
			// IGNORE - We haven't started yet.
			return;
		}
		// TODO - This does not yet work, because both, mouseClicked and
		// mouseDoubleClicked are invoked. If we do not want to create separate
		// listeners (one for starting to draw and one for the actual drawing),
		// we could as well tweak all of this into mouseClicked, and no longer
		// extending DoubleClickListener.
		// maybeFinishOrCancelEdge(e);
	}

	@Override
	public void mouseDoubleClickedLeft(final MouseEvent e) {
		if (edge != null) {
			maybeFinishOrCancelEdge(e);
		} else {
			final AbstractNode sourceNode = expectNode(e.getComponent());
			final Point point = pnGridPanel.getGridRelativeLocation(e.getLocationOnScreen());
			// This must cause listener suspension in various components!
			// TODO - use promise for edge!
			edge = pnGridPanel.createEdge(sourceNode, point);
			// The container should then implement createEdge, finishEdge,
			// removeEdge, etc.
			fire(EDGE_STARTED, e, sourceNode);
		}
	}

	private void maybeFinishOrCancelEdge(final MouseEvent e) {
		// TODO - this idiom is somewhat redundant:
		// Connecting existing edge:
		if (edge.acceptsTarget(e.getComponent())) {
			final AbstractNode targetNode = expectNode(e.getComponent());
			// // TODO - Edge should NOT require an edge creation listener!
			// edge.addMouseMotionListener(this);
			// edge.addMouseListener(this);
			// event intended to be ignored by gui.
			fire(EDGE_FINISHED, e, targetNode);
			eventBus.createEdge(new EdgeCreationCommand(this, edge.getSourceId(), edge.getTargetId()));
		} else {
			// TODO - nicer (should not call surrounding class):
			// The edge is not yet part of the model and could go to a
			// different layer!
			pnGridPanel.removeEdge(edge);
			// container.removeEdge(edge);
			fire(EDGE_CANCELLED, e, e.getComponent());
		}
		edge = null;
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		super.mouseMoved(e);
		if (edge != null) {
			fire(EDGE_CHANGED, e, e.getComponent());
			pnGridPanel.repaint(); // TODO - Why?
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		super.mouseEntered(e);
		if (edge != null) {
			fire(COMPONENT_ENTERED, e, e.getComponent());
		}
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		super.mouseExited(e);
		if (edge != null) {
			fire(COMPONENT_EXITED, e, e.getComponent());
		}
	}

}
