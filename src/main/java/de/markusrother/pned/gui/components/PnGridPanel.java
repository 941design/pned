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
import java.util.concurrent.TimeoutException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.EdgeCreator;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeCreator;
import de.markusrother.pned.gui.listeners.NodeLabelEditor;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.NodeSelector;
import de.markusrother.pned.gui.listeners.PnPopupListener;
import de.markusrother.pned.gui.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.menus.PnMenuFactory;
import de.markusrother.pned.gui.requests.NodeRequest;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.GridComponent;

/**
 * TODO - create MouseListener/Adapter which distinguishes between left and
 * right click!
 *
 * Each listener should have its own method to be added to components, because
 * it may be a MouseMotion- or a MouseListener, or both. That should not be the
 * callers responsibility.
 *
 * TODO - public method to add nodes at logical locations?
 *
 * TODO - Dispatch all events to all layers: the grid (node layer), the edge
 * layer, and possibly the root layer, using layer.dispatchEvent(e).
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnGridPanel extends JLayeredPane
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

	private final GuiEventBus eventBus;

	private final JComponent nodeLayer;
	private final JComponent edgeLayer;
	private final JComponent labelLayer;

	private final EdgeCreator edgeCreator;
	private final NodeCreator nodeCreator;
	private final NodeSelector multipleNodeSelector;

	private final PnPopupListener popupCreator;
	private final NodeLabelEditor nodeLabelEditor;

	// Stateful/Throwaway listeners:
	SelectionDragDropListener nodeSelectionDragListener;

	private final Set<AbstractNode> currentSelection;
	private final NodeFactory nodeFactory;
	private final EdgeFactory edgeFactory;

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
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param menuFactory
	 *            a {@link de.markusrother.pned.gui.menus.PnMenuFactory}
	 *            object.
	 * @param nodeFactory
	 *            a {@link de.markusrother.pned.gui.components.NodeFactory}
	 *            object.
	 */
	public PnGridPanel(final GuiEventBus eventBus, final PnMenuFactory menuFactory,
			final NodeFactory nodeFactory, final EdgeFactory edgeFactory) {

		this.eventBus = eventBus;
		this.nodeFactory = nodeFactory;
		this.edgeFactory = edgeFactory;
		this.currentSelection = new HashSet<>();

		setPreferredSize(preferredSize);
		// setBackground(Color.BLUE);
		// TODO - set the number of rectangles to be displayed:
		nodeLayer = new GridComponent(new Dimension(40, 40), Color.GRAY);
		// TODO - get preferred size from parent
		nodeLayer.setPreferredSize(preferredSize);
		// Must set bounds manually for JLayeredPane
		nodeLayer.setBounds(new Rectangle(new Point(0, 0), preferredSize));

		edgeLayer = createLayer(0);
		edgeLayer.setBackground(Color.LIGHT_GRAY);

		labelLayer = createLayer(10);

		// Listeners that are needed by children, are kept here:
		this.edgeCreator = new EdgeCreator(eventBus, edgeFactory, edgeLayer);
		this.nodeCreator = new NodeCreator(eventBus);
		this.multipleNodeSelector = new NodeSelector(eventBus);
		this.popupCreator = new PnPopupListener(menuFactory);
		this.nodeLabelEditor = new NodeLabelEditor(eventBus);

		add(nodeLayer, new Integer(1));
		nodeCreator.addToComponent(nodeLayer);
		edgeCreator.addToComponent(nodeLayer);
		popupCreator.addToComponent(nodeLayer);
		DragDropListener.addToComponent(nodeLayer, multipleNodeSelector);

		// FIXME - dispose!
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
	 * createLayer.
	 * </p>
	 *
	 * @param i
	 *            a int.
	 * @return a {@link javax.swing.JComponent} object.
	 */
	private JComponent createLayer(final int i) {
		final JComponent layer = new JComponent() {
			// TODO - For some strange reason we cannot add JPanels to
			// JLayeredPane
		};
		// Must set bounds manually for JLayeredPane
		layer.setBounds(new Rectangle(new Point(0, 0), preferredSize));
		add(layer, new Integer(i));
		return layer;
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

	/**
	 * <p>
	 * removeSelectedNodes.
	 * </p>
	 */
	public void removeSelectedNodes() {
		// FIXME - create a currentSelection object which listens to this event.
		// Use the RemoveSelectedNodesAction for this.
		// TODO - instead we could trigger the event below!
		for (final AbstractNode node : currentSelection) {
			eventBus.nodeRemoved(new NodeRemovalCommand(this, node.getId()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		final String placeId = cmd.getNodeId();
		final Point origin = cmd.getPoint();
		final Place place = nodeFactory.newPlace(placeId);
		addNodeComponent(place, origin);
		createLabelFor(place);
		place.setEdgeCreationListener(edgeCreator);
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		final String transitionId = cmd.getNodeId();
		final Point point = cmd.getPoint();
		final Transition transition = nodeFactory.newTransition(transitionId);
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
	private <T extends AbstractNode> void addNodeComponent(final T node, final Point origin) {
		// TODO - this method is too big!
		final Dimension d = node.getPreferredSize(); // FIXME - didn't we just
														// set the preferred
														// size!
		final Point nodeOrigin = origin.getLocation(); // TODO - Why?
		nodeOrigin.translate(-d.width / 2, -d.height / 2); // TODO - Why?
		node.setBounds(new Rectangle(nodeOrigin, node.getPreferredSize()));
		nodeLayer.add(node);
		nodeLayer.repaint(); // TODO - Why?
	}

	/**
	 * <p>
	 * createLabel.
	 * </p>
	 *
	 * @param node
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @return a {@link javax.swing.JLabel} object.
	 */
	private JLabel createLabelFor(final AbstractNode node) {
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
		final NodeLabel label = new NodeLabel(eventBus, nodeLabelEditor, nodeId);
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
	 *            a {@link de.markusrother.pned.gui.components.NodeLabel}
	 *            object.
	 * @param origin
	 *            a {@link java.awt.Point} object.
	 */
	private void addLabelComponent(final NodeLabel label, final Point origin) {
		label.setBounds(new Rectangle(origin, label.getPreferredSize()));
		labelLayer.add(label);
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final AbstractNode sourceNode = requestNode(cmd.getSourceId());
		final AbstractNode targetNode = requestNode(cmd.getTargetId());
		final EdgeComponent edge = new EdgeComponent(eventBus, //
				edgeId, //
				edgeFactory.getEdgeStyle(), //
				sourceNode, //
				targetNode);
		addEdgeComponent(edge);
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
	 * requestNode.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *         object.
	 */
	private AbstractNode requestNode(final String nodeId) {
		try {
			final NodeRequest req = new NodeRequest(this, nodeId);
			eventBus.requestNode(req);
			final AbstractNode node = req.get(); // FIXME - insert timeout here!
			return node;
		} catch (final TimeoutException e) {
			e.printStackTrace();
			// FIXME - create custom Exception
			throw new RuntimeException("TODO");
		}
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
		edge.setBounds(edgeLayer.getBounds());
		edgeLayer.add(edge);
		edgeLayer.revalidate();
		edgeLayer.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent event) {
		// TODO - changing selections are not yet repsected!
		// TODO - This could be done by the EventBus or some object listening to
		// the EventBus: PnedPropertyChangeMulticaster. However, We would still
		// have to duplicate that information here (e.g. node creation type).
		// Also, property changes are gui only.
		firePropertyChange("multiselection", false, true);
		currentSelection.addAll(event.getNodes());
		// TODO - Extract MultiSelectionDragDropListener
		nodeSelectionDragListener = new SelectionDragDropListener(eventBus, currentSelection);
		for (final AbstractNode node : currentSelection) {
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
	private void deselect(final Collection<AbstractNode> nodes) {
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
		// IGNORE, FIXME ??
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
	public void edgeMoved(final EdgeEditEvent e) {
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
		nodeCreator.removeFromComponent(nodeLayer);
		popupCreator.removeFromComponent(nodeLayer);
	}

	/**
	 * <p>
	 * installListeners.
	 * </p>
	 */
	protected void installListeners() {
		nodeCreator.addToComponent(nodeLayer);
		popupCreator.addToComponent(nodeLayer);
	}

}
