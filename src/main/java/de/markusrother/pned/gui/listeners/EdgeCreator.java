package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.COMPONENT_ENTERED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.COMPONENT_EXITED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_CANCELLED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_FINISHED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_MOVED;
import static de.markusrother.pned.gui.events.EdgeEditEvent.Type.EDGE_STARTED;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.pned.gui.components.PnGridPanel;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.swing.MultiClickListener;

/**
 * TODO - This could be split into an initial listener for receiving the double
 * click and an EdgeDrawListener or EdgeEditor. That would avoid all the
 * duplicated edge != null checks.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeCreator extends MultiClickListener {

	// TODO - Drawing could also start upon exit!

	private final PnGridPanel pnGridPanel;

	private EdgeComponent edge;

	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * addToComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param listener
	 *            a {@link de.markusrother.pned.gui.listeners.EdgeCreator}
	 *            object.
	 */
	public static void addToComponent(final Component component, final EdgeCreator listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	/**
	 * <p>
	 * removeFromComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param listener
	 *            a {@link de.markusrother.pned.gui.listeners.EdgeCreator}
	 *            object.
	 */
	public static void removeFromComponent(final Component component, final EdgeCreator listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	@Override
	public void addToComponent(final Component component) {
		addToComponent(component, this);
	}

	@Override
	public void removeFromComponent(final Component component) {
		removeFromComponent(component, this);
	}

	/**
	 * <p>
	 * Constructor for EdgeCreator.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param pnGridPanel
	 *            a {@link de.markusrother.pned.gui.components.PnGridPanel}
	 *            object.
	 */
	public EdgeCreator(final GuiEventBus eventBus, final PnGridPanel pnGridPanel) {
		this.eventBus = eventBus;
		this.pnGridPanel = pnGridPanel;
	}

	/**
	 * <p>
	 * expectNode.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *         object.
	 */
	private AbstractNode expectNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			e.printStackTrace();
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	/**
	 * <p>
	 * getParentRelativeLocation.
	 * </p>
	 *
	 * @param e
	 *            a {@link java.awt.event.MouseEvent} object.
	 * @return a {@link java.awt.Point} object.
	 */
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

	@Override
	public void mouseClickedLeft(final MouseEvent e) {
		if (edge != null && !edge.acceptsTarget(e.getComponent())) {
			doCancelEdge(e);
		}
	}

	@Override
	public void mouseClickedRight(final MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseClickedMiddle(final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDoubleClickedLeft(final MouseEvent e) {
		if (edge != null) {
			maybeFinishOrCancelEdge(e);
			return;
		} else if (!(e.getComponent() instanceof AbstractNode)) {
			// IGNORE
			return;
		} else {
			final AbstractNode sourceNode = expectNode(e.getComponent());
			final Point point = pnGridPanel.getGridRelativeLocation(e);
			// This must cause listener suspension in various components!
			// TODO - use promise for edge!
			edge = pnGridPanel.createEdge(sourceNode, point);
			// The container should then implement createEdge, finishEdge,
			// removeEdge, etc.
			eventBus.edgeStarted(new EdgeEditEvent(this, //
					EDGE_STARTED, //
					edge, //
					getParentRelativeLocation(e), //
					sourceNode));
		}
	}

	/**
	 * <p>
	 * maybeFinishOrCancelEdge.
	 * </p>
	 *
	 * @param e
	 *            a {@link java.awt.event.MouseEvent} object.
	 */
	private void maybeFinishOrCancelEdge(final MouseEvent e) {
		// TODO - this idiom is somewhat redundant:
		// Connecting existing edge:
		if (edge.acceptsTarget(e.getComponent())) {
			final AbstractNode targetNode = expectNode(e.getComponent());
			// // TODO - Edge should NOT require an edge creation listener!
			// edge.addMouseMotionListener(this);
			// edge.addMouseListener(this);
			// event intended to be ignored by gui.
			eventBus.edgeFinished(new EdgeEditEvent(this, //
					EDGE_FINISHED, //
					edge, //
					getParentRelativeLocation(e), //
					targetNode));
			final String edgeId = eventBus.requestId();
			eventBus.createEdge(new EdgeCreationCommand(this, edgeId, edge.getSourceId(), edge.getTargetId()));
			edge = null;
		} else {
			doCancelEdge(e);
		}
	}

	private void doCancelEdge(final MouseEvent e) {
		// TODO - nicer (should not call surrounding class):
		// The edge is not yet part of the model and could go to a
		// different layer!
		pnGridPanel.removeEdge(edge);
		// container.removeEdge(edge);
		eventBus.edgeCancelled(new EdgeEditEvent(this, //
				EDGE_CANCELLED, //
				edge, //
				getParentRelativeLocation(e), //
				e.getComponent()));
		edge = null;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(final MouseEvent e) {
		super.mouseMoved(e);
		if (edge != null) {
			eventBus.edgeMoved(new EdgeEditEvent(this, //
					EDGE_MOVED, //
					edge, //
					getParentRelativeLocation(e), //
					e.getComponent()));
			pnGridPanel.revalidate(); // TODO - Why?
			pnGridPanel.repaint(); // TODO - Why?
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(final MouseEvent e) {
		super.mouseEntered(e);
		if (edge != null) {
			eventBus.componentEntered(new EdgeEditEvent(this, //
					COMPONENT_ENTERED, //
					edge, //
					getParentRelativeLocation(e), //
					e.getComponent()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final MouseEvent e) {
		super.mouseExited(e);
		if (edge != null) {
			eventBus.componentExited(new EdgeEditEvent(this, //
					COMPONENT_EXITED, //
					edge, //
					getParentRelativeLocation(e), //
					e.getComponent()));
		}
	}

}
