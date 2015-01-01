package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.NodeRemovalEvent;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.RemoveSelectedNodesEvent;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.events.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.listeners.EdgeCreator;
import de.markusrother.pned.gui.listeners.NodeCreator;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.NodeSelector;
import de.markusrother.pned.gui.listeners.PnGridPopupListener;
import de.markusrother.pned.gui.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;
import de.markusrother.pned.gui.requests.NodeRequest;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.snap.SnapGridComponent;

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
		NodeSelectionListener,
		NodeListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeRemovalListener {

	// TODO - This could be properties!
	public enum State {
		MULTISELECTION, //
		PLACE_CREATION, //
		TRANSITION_CREATION, //
	}

	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(500, 500);
	/** Constant <code>transitionDimensions</code> */
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	/** Constant <code>placeDimensions</code> */
	private static final Dimension placeDimensions = new Dimension(50, 50);
	/** Constant <code>labelHeight=20</code> */
	private static final int labelHeight = 20;

	/** Constant <code>defaultState</code> */
	private static final EnumSet<State> defaultState = EnumSet.of(State.PLACE_CREATION);

	private final GuiEventBus eventBus;

	private final JComponent nodeLayer;
	private final JComponent edgeLayer;
	private final JComponent labelLayer;

	private final EdgeCreator edgeCreator;
	private final MouseAdapter nodeCreator;
	private final NodeSelector multipleNodeSelector;
	private final SingleNodeSelector singleNodeSelector;
	private final PnGridPopupListener popupCreator;

	private final EnumSet<State> state;
	// Stateful/Throwaway listeners:
	SelectionDragDropListener nodeSelectionDragListener;

	private final Set<AbstractNode> currentSelection;

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
	 * <p>
	 * getCenter.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public static Point getCenter(final Component component) {
		final Point point = component.getLocation();
		point.translate( //
				(int) Math.floor((component.getWidth() + 0.5) / 2.0), //
				(int) Math.floor((component.getHeight() + 0.5) / 2.0));
		return point;
	}

	/**
	 * I don't quite like passing this to other classes/methods/constructors,
	 * while this is not fully initialized!
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public PnGridPanel(final GuiEventBus eventBus) {

		this.eventBus = eventBus;

		this.state = defaultState;

		// TODO - add scroll panel
		setPreferredSize(preferredSize);
		// setBackground(Color.BLUE);
		// TODO - set the number of rectangles to be displayed:
		nodeLayer = new SnapGridComponent(new Dimension(40, 40), Color.GRAY);
		// TODO - get preferred size from parent
		nodeLayer.setPreferredSize(preferredSize);
		// Must set bounds manually for JLayeredPane
		nodeLayer.setBounds(new Rectangle(new Point(0, 0), preferredSize));

		edgeLayer = createLayer(0);
		edgeLayer.setBackground(Color.LIGHT_GRAY);

		labelLayer = createLayer(10);

		// new ListenerManager(State) {
		// List<EventListener> getListeners(EnumSet<State> enumSet) {
		// if...else
		// }
		// }
		// Listeners that are needed by children, are kept here:
		edgeCreator = new EdgeCreator(eventBus, this);
		nodeCreator = new NodeCreator(eventBus);
		multipleNodeSelector = new NodeSelector(eventBus);
		singleNodeSelector = new SingleNodeSelector(eventBus);
		popupCreator = new PnGridPopupListener(eventBus, this);

		currentSelection = new HashSet<>();

		add(nodeLayer, new Integer(1));
		nodeLayer.addMouseListener(nodeCreator);
		nodeLayer.addMouseMotionListener(edgeCreator);
		nodeLayer.addMouseListener(popupCreator);
		DragDropListener.addToComponent(nodeLayer, multipleNodeSelector);

		// FIXME - dispose!
		eventBus.addListener(NodeSelectionListener.class, this);
		eventBus.addListener(NodeListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
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
	 * @param pointOnScreen
	 *            a {@link java.awt.Point} object.
	 * @return a {@link java.awt.Point} object.
	 */
	public Point getGridRelativeLocation(final Point pointOnScreen) {
		return delta(pointOnScreen, nodeLayer.getLocationOnScreen());
	}

	/**
	 * <p>
	 * hasState.
	 * </p>
	 *
	 * @param state
	 *            a
	 *            {@link de.markusrother.pned.gui.components.PnGridPanel.State}
	 *            object.
	 * @return a boolean.
	 */
	public boolean hasState(final State state) {
		return this.state.contains(state);
	}

	/**
	 * <p>
	 * addState.
	 * </p>
	 *
	 * @param state
	 *            a
	 *            {@link de.markusrother.pned.gui.components.PnGridPanel.State}
	 *            object.
	 */
	protected void addState(final State state) {
		this.state.add(state);
	}

	/**
	 * <p>
	 * removeState.
	 * </p>
	 *
	 * @param state
	 *            a
	 *            {@link de.markusrother.pned.gui.components.PnGridPanel.State}
	 *            object.
	 */
	protected void removeState(final State state) {
		this.state.remove(state);
	}

	/**
	 * <p>
	 * removeSelectedNodes.
	 * </p>
	 */
	public void removeSelectedNodes() {
		// TODO - instead we could trigger the event below!
		for (final AbstractNode node : currentSelection) {
			eventBus.nodeRemoved(new NodeRemovalEvent(this, node.getId()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		// TODO - use currentPlaceStyle!
		final Place place = new Place(eventBus, (int) placeDimensions.getWidth());
		addNodeComponent(place, cmd.getPoint());
		place.setId(cmd.getNodeId());
		addListeners(place);
		createLabel(place);
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		final Transition transition = new Transition(eventBus, (int) transitionDimensions.getWidth());
		addNodeComponent(transition, cmd.getPoint());
		transition.setId(cmd.getNodeId());
		addListeners(transition);
		createLabel(transition);
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
		final Dimension d = node.getPreferredSize();
		final Point nodeOrigin = origin.getLocation(); // TODO - Why?
		nodeOrigin.translate(-d.width / 2, -d.height / 2); // TODO - Why?
		node.setBounds(new Rectangle(nodeOrigin, node.getPreferredSize()));
		nodeLayer.add(node);
		nodeLayer.repaint(); // TODO - Why?
	}

	/**
	 * <p>
	 * addListeners.
	 * </p>
	 *
	 * @param node
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 */
	private void addListeners(final AbstractNode node) {
		node.setSingleNodeSelector(singleNodeSelector);
		node.setEdgeCreationListener(edgeCreator);
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
	private JLabel createLabel(final AbstractNode node) {
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
		final NodeLabel label = new NodeLabel(eventBus, nodeId);
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
		final AbstractNode sourceNode = requestNode(cmd.getSourceId());
		final AbstractNode targetNode = requestNode(cmd.getTargetId());
		final EdgeComponent edge = new EdgeComponent(eventBus, sourceNode, targetNode);
		addEdgeComponent(edge);
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
			final AbstractNode node = req.get();
			return node;
		} catch (final TimeoutException e) {
			// FIXME - create custom Exception
			throw new RuntimeException("TODO");
		}
	}

	/**
	 * <p>
	 * createEdge.
	 * </p>
	 *
	 * @param sourceNode
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.pned.gui.components.EdgeComponent}
	 *         object.
	 */
	public EdgeComponent createEdge(final AbstractNode sourceNode, final Point target) {
		final Point source = getCenter(sourceNode);
		final EdgeComponent edge = new EdgeComponent(eventBus, sourceNode, source, target);
		edge.setBounds(this.getBounds()); // OBSOLETE?
		addEdgeComponent(edge);
		return edge;
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
		edge.setBounds(this.getBounds()); // OBSOLETE?
		edgeLayer.add(edge);
		edgeLayer.repaint();
	}

	/**
	 * <p>
	 * removeEdge.
	 * </p>
	 *
	 * @param edge
	 *            a {@link de.markusrother.pned.gui.components.EdgeComponent}
	 *            object.
	 */
	public void removeEdge(final EdgeComponent edge) {
		nodeLayer.remove(edge);
		nodeLayer.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// TODO - changing selections are not yet repsected!
		// TODO - only add new state if it was not selected before.
		addState(State.MULTISELECTION);
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
	public void nodesUnselected(final NodeSelectionEvent event) {
		deselect(event.getNodes());
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
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
			removeState(State.MULTISELECTION);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		switch (cmd.getMode()) {
		case PLACE:
			removeState(State.TRANSITION_CREATION);
			addState(State.PLACE_CREATION);
			break;
		case TRANSITION:
			removeState(State.PLACE_CREATION);
			addState(State.TRANSITION_CREATION);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
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
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		// IGNORE
	}

}
