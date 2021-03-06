package de.markusrother.pned.gui.components.listeners;

import static de.markusrother.pned.gui.control.events.EdgeEditEvent.Type.COMPONENT_ENTERED;
import static de.markusrother.pned.gui.control.events.EdgeEditEvent.Type.COMPONENT_EXITED;
import static de.markusrother.pned.gui.control.events.EdgeEditEvent.Type.EDGE_CANCELLED;
import static de.markusrother.pned.gui.control.events.EdgeEditEvent.Type.EDGE_FINISHED;
import static de.markusrother.pned.gui.control.events.EdgeEditEvent.Type.EDGE_STARTED;
import static de.markusrother.swing.SwingUtils.getCenter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.pned.gui.components.EdgeComponentFactory;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.events.EdgeEditEvent;
import de.markusrother.swing.MultiClickListener;

/**
 * <p>
 * Creator of {@link de.markusrother.pned.gui.components.EdgeComponent}s,
 * responding to mouse events while drawing the edge.
 * </p>
 * 
 * TODO
 * <ul>
 * <li>This could be split into a) one listener for receiving the double click
 * event and b) another listener for executing the actual drawing. That would
 * avoid all the duplicated edge != null checks.</li>
 * <li>Drawing should start upon exiting a component</li>
 * </ul>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class EdgeCreator extends MultiClickListener {

    private final PnEventBus eventBus;
    private final EdgeComponentFactory edgeFactory;
    private final Container container;

    private EdgeComponent edge;

    /**
     * <p>
     * addToComponent.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     * @param listener
     *            a
     *            {@link de.markusrother.pned.gui.components.listeners.EdgeCreator}
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
     *            a
     *            {@link de.markusrother.pned.gui.components.listeners.EdgeCreator}
     *            object.
     */
    public static void removeFromComponent(final Component component, final EdgeCreator listener) {
        component.removeMouseListener(listener);
        component.removeMouseMotionListener(listener);
    }

    /**
     * <p>
     * Constructor for EdgeCreator.
     * </p>
     *
     * @param eventBus
     *            a {@link de.markusrother.pned.control.EventBus} object.
     * @param container
     *            a {@link java.awt.Container} object.
     * @param edgeFactory
     *            a
     *            {@link de.markusrother.pned.gui.components.EdgeComponentFactory}
     *            object.
     */
    public EdgeCreator(final PnEventBus eventBus, final EdgeComponentFactory edgeFactory, final Container container) {
        this.eventBus = eventBus;
        this.edgeFactory = edgeFactory;
        this.container = container;
    }

    /** {@inheritDoc} */
    @Override
    public void addToComponent(final Component component) {
        addToComponent(component, this);
    }

    /** {@inheritDoc} */
    @Override
    public void removeFromComponent(final Component component) {
        removeFromComponent(component, this);
    }

    /**
     * <p>
     * expectNode.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     * @return a
     *         {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
     *         object.
     */
    private AbstractNodeComponent expectNode(final Component component) {
        try {
            return (AbstractNodeComponent) component;
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

    /** {@inheritDoc} */
    @Override
    public void mouseClickedLeft(final MouseEvent e) {
        if (edge != null && !edge.acceptsTarget(e.getComponent())) {
            doCancelEdge(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClickedRight(final MouseEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClickedMiddle(final MouseEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDoubleClickedLeft(final MouseEvent e) {
        final Component component = e.getComponent();
        if (edge == null) {
            if (component instanceof AbstractNodeComponent) {
                doStartEdge(e);
            }
        } else {
            if (edge.acceptsTarget(component)) {
                doFinishEdge(e);
            } else {
                doCancelEdge(e);
            }
        }
    }

    /**
     * <p>
     * doStartEdge.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doStartEdge(final MouseEvent e) {
        final AbstractNodeComponent sourceNode = expectNode(e.getComponent());
        final Point target = e.getPoint();
        final Point origin = sourceNode.getLocation();
        target.translate(origin.x, origin.y);
        final Point source = getCenter(sourceNode);
        // TODO - Constructor should require center(source) and target points.
        edge = new EdgeComponent(eventBus, //
                edgeFactory.getEdgeStyle(), //
                sourceNode,//
                source,//
                target);
        // We must set bounds origin to (0,0), because it may become negative
        // when viewport changes:
        edge.setBounds(new Rectangle(new Point(0, 0), container.getPreferredSize()));
        container.add(edge);
        container.repaint();
        // Must add to edge component because the edge has the same bounds as
        // container and would keep events from bubbling!
        addToComponent(edge);
        eventBus.edgeStarted(new EdgeEditEvent(this, //
                EDGE_STARTED, //
                edge, //
                target, //
                sourceNode));
    }

    /**
     * <p>
     * doFinishEdge.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doFinishEdge(final MouseEvent e) {
        final AbstractNodeComponent targetNode = expectNode(e.getComponent());
        eventBus.edgeFinished(new EdgeEditEvent(this, //
                EDGE_FINISHED, //
                edge, //
                getParentRelativeLocation(e), //
                targetNode));
        final String edgeId = eventBus.requestId();
        eventBus.createEdge(new EdgeCreationCommand(this, //
                edgeId, //
                edge.getSourceId(), //
                edge.getTargetId()));
        edge = null;
    }

    /**
     * <p>
     * doCancelEdge.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doCancelEdge(final MouseEvent e) {
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
            doMoveEdge(e);
        }
    }

    /**
     * <p>
     * doMoveEdge.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doMoveEdge(final MouseEvent e) {
        edge.setUnboundTarget(getParentRelativeLocation(e));
        container.revalidate();
        container.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void mouseEntered(final MouseEvent e) {
        super.mouseEntered(e);
        if (edge != null) {
            doEnterComponent(e);
        }
    }

    /**
     * <p>
     * doEnterComponent.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doEnterComponent(final MouseEvent e) {
        // TODO - Let transitions listen to this event to do preview
        // highlighting.
        eventBus.componentEntered(new EdgeEditEvent(this, //
                COMPONENT_ENTERED, //
                edge, //
                getParentRelativeLocation(e), //
                e.getComponent()));
    }

    /** {@inheritDoc} */
    @Override
    public void mouseExited(final MouseEvent e) {
        super.mouseExited(e);
        if (edge != null) {
            doExitComponent(e);
        }
    }

    /**
     * <p>
     * doExitComponent.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void doExitComponent(final MouseEvent e) {
        eventBus.componentExited(new EdgeEditEvent(this, //
                COMPONENT_EXITED, //
                edge, //
                getParentRelativeLocation(e), //
                e.getComponent()));
    }

}
