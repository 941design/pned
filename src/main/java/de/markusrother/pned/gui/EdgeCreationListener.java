package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.EdgeEditEvent.Type.COMPONENT_ENTERED;
import static de.markusrother.pned.gui.EdgeEditEvent.Type.COMPONENT_EXITED;
import static de.markusrother.pned.gui.EdgeEditEvent.Type.EDGE_CANCELLED;
import static de.markusrother.pned.gui.EdgeEditEvent.Type.EDGE_CHANGED;
import static de.markusrother.pned.gui.EdgeEditEvent.Type.EDGE_FINISHED;
import static de.markusrother.pned.gui.EdgeEditEvent.Type.EDGE_STARTED;
import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import de.markusrother.swing.DoubleClickListener;

public class EdgeCreationListener extends DoubleClickListener {

	// TODO - Drawing could also start upon exit!

	private final PnGridPanel pnGridPanel;

	private EdgeComponent edge;

	public static void addToComponent(final Component component, final EdgeCreationListener listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public static void removeFromComponent(final Component component, final EdgeCreationListener listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	EdgeCreationListener(final PnGridPanel pnGridPanel) {
		this.pnGridPanel = pnGridPanel;
	}

	private void startNewEdge(final AbstractNode source, final Point point) {
		// TODO - Cannot be event, because edge is not complete, yet.
		// Could be a gui-only event, though. Here, we must suspend listeners in
		// all nodes.
		edge = pnGridPanel.createEdge(source, point);
		// The container should then implement createEdge, finishEdge,
		// removeEdge, etc.
	}

	private void finishCurrentEdge(final AbstractNode targetNode) {
		edge.setTargetComponent(targetNode);
		edge.finishedDrawing();
		// This is needed for future edges, because the edge
		// component overlaps the grid!
		// TODO - maybe the edge should in turn receive a listener for edge
		// creation events.
		edge.addMouseMotionListener(this);
		edge.addMouseListener(this);
	}

	private AbstractNode expectNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private void fire(final EdgeEditEvent e) {
		eventBus.fireEdgeEditEvent(e);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);

	}

	@Override
	public void mouseDoubleClicked(final MouseEvent e) {
		if (!SwingUtilities.isLeftMouseButton(e)) {
			return;
		}
		if (edge != null) {
			// TODO - this idiom is somewhat redundant:
			// Connecting existing edge:
			if (edge.acceptsTarget(e.getComponent())) {
				final AbstractNode targetNode = expectNode(e.getComponent());
				finishCurrentEdge(targetNode);
				fire(new EdgeEditEvent(EDGE_FINISHED, this, edge));
			} else {
				// TODO - nicer (should not call surrounding class):
				// The edge is not yet part of the model and could go to a
				// different layer!
				pnGridPanel.removeEdge(edge);
				// container.removeEdge(edge);
				fire(new EdgeEditEvent(EDGE_CANCELLED, this, edge));
			}
			edge = null;
		} else {
			final AbstractNode sourceNode = expectNode(e.getComponent());
			final Point point = pnGridPanel.getGridRelativeLocation(e.getLocationOnScreen());
			startNewEdge(sourceNode, point);
			fire(new EdgeEditEvent(EDGE_STARTED, this, edge));
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		super.mouseMoved(e);
		if (edge != null) {
			fire(new EdgeEditEvent(EDGE_CHANGED, this));
			if (!edge.hasTargetComponent()) {
				edge.setUnboundTarget(pnGridPanel.getGridRelativeLocation(e.getLocationOnScreen()));
			} else if (edge.getTargetComponent() != e.getComponent()) {
				edge.removeTargetComponent();
				edge.setUnboundTarget(pnGridPanel.getGridRelativeLocation(e.getLocationOnScreen()));
			}
			// TODO - This causes flickering, whereas the solution with
			// drawing from the center is more pleasing but does not work
			// with transparent elements. The cleanest solution would be to
			// manage the edge points at the edge component, too. Then we
			// can determine the drawing direction more precisely without
			// the flickering.
			// edge.connectToSource(edge.getSourceComponent());
			pnGridPanel.repaint();
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		super.mouseEntered(e);
		if (edge != null) {
			fire(new EdgeEditEvent(COMPONENT_ENTERED, this, e.getComponent()));
			final Component possibleTarget = e.getComponent();
			if (edge.acceptsTarget(possibleTarget)) {
				edge.setTargetComponent((AbstractNode) possibleTarget);
				edge.highlightValid();
			} else {
				edge.highlightInvalid();
			}
		}
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		super.mouseExited(e);
		if (edge != null) {
			fire(new EdgeEditEvent(COMPONENT_EXITED, this, e.getComponent()));
			edge.highlightStandard();
		}
	}

}
