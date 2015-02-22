package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.gui.components.listeners.EdgeCreator;
import de.markusrother.pned.gui.components.listeners.NodeCreator;
import de.markusrother.pned.gui.components.listeners.NodeLabelEditor;
import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.components.listeners.NodeSelector;
import de.markusrother.pned.gui.components.listeners.PnPopupListener;
import de.markusrother.pned.gui.components.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.components.menus.PnMenuFactory;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.events.EdgeEditEvent;
import de.markusrother.pned.gui.control.events.EdgeEditListener;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.NodeSelectionListener;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.control.requests.NodeRequest;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.GridComponent;

/**
 * <p>
 * PnGridPanel class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnGridPanel extends GridComponent
    implements
        NodeCreationListener,
        NodeSelectionListener,
        NodeRemovalListener,
        EdgeCreationListener,
        EdgeEditListener {

    /** Constant <code>preferredSize</code> */
    private static final Dimension preferredSize = new Dimension(3000, 3000);
    /** Constant <code>labelHeight=20</code> */
    private static final int labelHeight = 20;

    private final PnEventBus eventBus;

    private final EdgeCreator edgeCreator;
    private final NodeCreator nodeCreator;
    private final NodeSelector multipleNodeSelector;

    private final PnPopupListener popupCreator;
    private final NodeLabelEditor nodeLabelEditor;

    // Stateful/Throwaway listeners:
    SelectionDragDropListener nodeSelectionDragListener;

    private final Set<AbstractNodeComponent> currentSelection;
    private final NodeComponentFactory nodeFactory;
    private final EdgeComponentFactory edgeFactory;

    /**
     * <p>
     * delta.
     * </p>
     *
     * @param a
     *            a {@link java.awt.Point} object.
     * @param b
     *            a {@link java.awt.Point} object.
     * @return a {@link java.awt.Point} object.
     */
    public static Point delta(final Point a, final Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    /**
     * I don't quite like passing this to other classes/methods/constructors,
     * while this is not fully initialized!
     *
     * @param eventBus
     *            a {@link de.markusrother.pned.control.EventBus} object.
     * @param menuFactory
     *            a
     *            {@link de.markusrother.pned.gui.components.menus.PnMenuFactory}
     *            object.
     * @param nodeFactory
     *            a
     *            {@link de.markusrother.pned.gui.components.NodeComponentFactory}
     *            object.
     * @param edgeFactory
     *            a
     *            {@link de.markusrother.pned.gui.components.EdgeComponentFactory}
     *            object.
     */
    public PnGridPanel(final PnEventBus eventBus,
            final PnMenuFactory menuFactory,
            final NodeComponentFactory nodeFactory,
            final EdgeComponentFactory edgeFactory) {
        super(new Dimension(40, 40), Color.GRAY);

        this.eventBus = eventBus;
        this.nodeFactory = nodeFactory;
        this.edgeFactory = edgeFactory;
        this.currentSelection = new HashSet<>();

        setPreferredSize(preferredSize);

        // Listeners that are needed by children, are kept here:
        this.edgeCreator = new EdgeCreator(eventBus, edgeFactory, this);
        this.nodeCreator = new NodeCreator(eventBus);
        this.multipleNodeSelector = new NodeSelector(eventBus);
        this.popupCreator = new PnPopupListener(menuFactory);
        this.nodeLabelEditor = new NodeLabelEditor(eventBus);

        nodeCreator.addToComponent(this);
        edgeCreator.addToComponent(this);
        popupCreator.addToComponent(this);
        DragDropListener.addToComponent(this, multipleNodeSelector);

        // TODO - dispose and assert removal of listeners!
        eventBus.addListener(NodeCreationListener.class, this);
        eventBus.addListener(NodeSelectionListener.class, this);
        eventBus.addListener(NodeRemovalListener.class, this);
        eventBus.addListener(EdgeCreationListener.class, this);
        // TODO - This is currently without effect, because edge components are
        // laid on top and prevent mouse events from bubbling!
        eventBus.addListener(EdgeEditListener.class, this);
    }

    /**
     * <p>
     * getGridRelativeLocation.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     * @return a {@link java.awt.Point} object.
     */
    public Point getGridRelativeLocation(final MouseEvent e) {
        final Point offset = e.getPoint().getLocation();
        Component component = (Component) e.getSource();
        while (component != this) {
            if (component.getParent() == null) {
                throw new IllegalStateException("Trying to retrieve relative location of a non-child.");
            }
            final Point location = component.getLocation();
            offset.translate(location.x, location.y);
            component = component.getParent();
        }
        return offset;
    }

    /** {@inheritDoc} */
    @Override
    public void createPlace(final PlaceCreationCommand cmd) {
        final String placeId = cmd.getNodeId();
        final Point origin = cmd.getPoint();
        final PlaceComponent place = nodeFactory.newPlace(placeId);
        addNodeComponent(place, origin);
        createLabelFor(place);
        place.setEdgeCreationListener(edgeCreator);
    }

    /** {@inheritDoc} */
    @Override
    public void createTransition(final TransitionCreationCommand cmd) {
        final String transitionId = cmd.getNodeId();
        final Point point = cmd.getPoint();
        final TransitionComponent transition = nodeFactory.newTransition(transitionId);
        addNodeComponent(transition, point);
        createLabelFor(transition);
        transition.setEdgeCreationListener(edgeCreator);
    }

    /**
     * <p>
     * addNodeComponent.
     * </p>
     *
     * @param node
     *            a T object.
     * @param origin
     *            a {@link java.awt.Point} object.
     * @param <T>
     *            a T object.
     */
    private <T extends AbstractNodeComponent> void addNodeComponent(final T node, final Point origin) {
        final Dimension d = node.getPreferredSize();
        final Point nodeOrigin = origin.getLocation();
        final Dimension preferredSize = getPreferredSize();
        final int x = nodeOrigin.x > preferredSize.width - 500 ? nodeOrigin.x + 500 : preferredSize.width;
        final int y = nodeOrigin.y > preferredSize.height - 500 ? nodeOrigin.y + 500 : preferredSize.height;
        setPreferredSize(new Dimension(x, y));
        // Centering the node component around the requested point:
        nodeOrigin.translate(-d.width / 2, -d.height / 2);
        node.setBounds(new Rectangle(nodeOrigin, node.getPreferredSize()));
        add(node);
        repaint();
    }

    /**
     * <p>
     * createLabel.
     * </p>
     *
     * @param node
     *            a
     *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
     *            object.
     * @return a {@link javax.swing.JLabel} object.
     */
    private JLabel createLabelFor(final AbstractNodeComponent node) {
        final Point labelOrigin = node.getLocation();
        labelOrigin.translate(0, -labelHeight);
        return createLabel(labelOrigin, node.getId());
    }

    /**
     * <p>
     * createLabel.
     * </p>
     *
     * @param origin
     *            a {@link java.awt.Point} object.
     * @param nodeId
     *            a {@link java.lang.String} object.
     * @return a {@link javax.swing.JLabel} object.
     */
    public JLabel createLabel(final Point origin, final String nodeId) {
        // TODO - We could create an edge that connects label with node, synced
        // similarly to the node.
        final LabelComponent label = new LabelComponent(eventBus, nodeLabelEditor, nodeId);
        addLabelComponent(label, origin);
        // eventBus.fireLabelCreatedEvent(null);
        return label;
    }

    /**
     * <p>
     * addLabelComponent.
     * </p>
     *
     * @param label
     *            a {@link de.markusrother.pned.gui.components.LabelComponent}
     *            object.
     * @param origin
     *            a {@link java.awt.Point} object.
     */
    private void addLabelComponent(final LabelComponent label, final Point origin) {
        label.setBounds(new Rectangle(origin, label.getPreferredSize()));
        add(label);
    }

    /** {@inheritDoc} */
    @Override
    public void createEdge(final EdgeCreationCommand cmd) {
        final String edgeId = cmd.getEdgeId();
        final AbstractNodeComponent sourceNode = NodeRequest.doRequestNode(cmd.getSourceId(), eventBus);
        final AbstractNodeComponent targetNode = NodeRequest.doRequestNode(cmd.getTargetId(), eventBus);
        final EdgeComponent edge = new EdgeComponent(eventBus, //
                edgeId, //
                edgeFactory.getEdgeStyle(), //
                sourceNode, //
                targetNode);
        addEdgeComponent(edge);
        // EdgeComponents must respond to resizing by also adjusting their bound
        // to match ours.
        addComponentListener(edge);
    }

    /**
     * {@inheritDoc}
     *
     * Edges remove themselves!
     */
    @Override
    public void removeEdge(final EdgeRemoveCommand cmd) {
        // IGNORE
    }

    /**
     * <p>
     * addEdgeComponent.
     * </p>
     *
     * @param edge
     *            a {@link de.markusrother.pned.gui.components.EdgeComponent}
     *            object.
     */
    private void addEdgeComponent(final EdgeComponent edge) {
        // We must set bounds origin to (0,0), because it may become negative
        // when viewport changes:
        edge.setBounds(new Rectangle(new Point(0, 0), getPreferredSize()));
        add(edge);
        revalidate();
        repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void nodesSelected(final NodeMultiSelectionEvent event) {
        firePropertyChange("multiselection", false, true);
        currentSelection.addAll(event.getNodes());
        // TODO - Extract MultiSelectionDragDropListener
        nodeSelectionDragListener = new SelectionDragDropListener(eventBus, currentSelection);
        for (final AbstractNodeComponent node : currentSelection) {
            node.setDragDropListener(nodeSelectionDragListener);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Event source may be gridPanel or another listener, model etc.
     */
    @Override
    public void nodesUnselected(final NodeMultiSelectionEvent event) {
        deselect(event.getNodes());
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionCancelled(final NodeMultiSelectionEvent event) {
        deselect(currentSelection);
    }

    /**
     * <p>
     * deselect.
     * </p>
     *
     * @param nodes
     *            a {@link java.util.Collection} object.
     */
    private void deselect(final Collection<AbstractNodeComponent> nodes) {
        // NOTE - How nodes are displayed is handled by the nodes
        // themselves.
        currentSelection.removeAll(nodes);
        if (currentSelection.isEmpty()) {
            // We dont want to use property change listeners because we need to
            // connect references explicitly, adding listeners to all event
            // sources.
        }
    }

    /** {@inheritDoc} */
    @Override
    public void nodeRemoved(final NodeRemovalCommand e) {
        // NOTE - Nodes, labels, and edges remove themselves.
        repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
        // NOTE - Nodes, labels, and edges remove themselves.
        repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionFinished(final NodeMultiSelectionEvent event) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void componentEntered(final EdgeEditEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void componentExited(final EdgeEditEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void edgeCancelled(final EdgeEditEvent e) {
        installListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void edgeFinished(final EdgeEditEvent e) {
        installListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void edgeStarted(final EdgeEditEvent e) {
        suspendListeners();
    }

    /**
     * <p>
     * suspendListeners.
     * </p>
     */
    protected void suspendListeners() {
        nodeCreator.removeFromComponent(this);
        popupCreator.removeFromComponent(this);
    }

    /**
     * <p>
     * installListeners.
     * </p>
     */
    protected void installListeners() {
        nodeCreator.addToComponent(this);
        popupCreator.addToComponent(this);
    }

}
