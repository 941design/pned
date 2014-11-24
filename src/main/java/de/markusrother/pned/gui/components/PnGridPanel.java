package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.markusrother.concurrent.Promise;
import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.NodeSelector;
import de.markusrother.pned.gui.listeners.PnGridPopupListener;
import de.markusrother.pned.gui.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;
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
 */
public class PnGridPanel extends JLayeredPane
	implements
		NodeSelectionListener,
		NodeListener {

	// TODO - This could be properties!
	public enum State {
		MULTISELECTION, //
		PLACE_CREATION, //
		TRANSITION_CREATION, //
	}

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	private static final Dimension placeDimensions = new Dimension(50, 50);
	private static final int labelHeight = 20;

	private static final EnumSet<State> defaultState = EnumSet.of(State.PLACE_CREATION);

	public static EventBus eventBus;

	private final JComponent nodeLayer;
	private final JComponent edgeLayer;
	private final JComponent labelLayer;

	private final EdgeCreationListener edgeCreator;
	private final MouseAdapter nodeCreator;
	private final NodeSelector multipleNodeSelector;
	private final SingleNodeSelector singleNodeSelector;
	private final PnGridPopupListener popupCreator;

	private final EnumSet<State> state;
	// Stateful/Throwaway listeners:
	SelectionDragDropListener nodeSelectionDragListener;

	private final Set<AbstractNode> currentSelection;

	public static Point delta(final Point a, final Point b) {
		return new Point(a.x - b.x, a.y - b.y);
	}

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
	 */
	PnGridPanel() {

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
		edgeCreator = new EdgeCreationListener(this);
		nodeCreator = new NodeCreator();
		multipleNodeSelector = new NodeSelector();
		singleNodeSelector = new SingleNodeSelector();
		popupCreator = new PnGridPopupListener(this);

		currentSelection = new HashSet<>();

		add(nodeLayer, new Integer(1));
		nodeLayer.addMouseListener(nodeCreator);
		nodeLayer.addMouseMotionListener(edgeCreator);
		nodeLayer.addMouseListener(popupCreator);
		DragDropListener.addToComponent(nodeLayer, multipleNodeSelector);

		// TODO - Make this a lazy initialized singleton! First try if a static
		// singleton works, too
		eventBus = new EventBus();
		eventBus.addNodeSelectionListener(this);
		eventBus.addNodeListener(this);
	}

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

	public Point getGridRelativeLocation(final Point pointOnScreen) {
		return delta(pointOnScreen, nodeLayer.getLocationOnScreen());
	}

	public boolean hasState(final State state) {
		return this.state.contains(state);
	}

	protected void addState(final State state) {
		this.state.add(state);
	}

	protected void removeState(final State state) {
		this.state.remove(state);
	}

	public Place createPlace(final Point point) {
		final Place place = new Place(placeDimensions);
		addNodeComponent(place, point);
		place.setSingleNodeSelector(singleNodeSelector);
		place.setEdgeCreationListener(edgeCreator);
		return place;
	}

	public Transition createTransition(final Point point) {
		final Transition transition = new Transition(transitionDimensions);
		addNodeComponent(transition, point);
		transition.setSingleNodeSelector(singleNodeSelector);
		transition.setEdgeCreationListener(edgeCreator);
		return transition;
	}

	private <T extends AbstractNode> void addNodeComponent(final T node, final Point origin) {
		// TODO - this method is too big!
		final Dimension d = node.getPreferredSize();
		final Point nodeOrigin = origin.getLocation(); // TODO - Why?
		nodeOrigin.translate(-d.width / 2, -d.height / 2); // TODO - Why?
		node.setBounds(new Rectangle(nodeOrigin, node.getPreferredSize()));
		nodeLayer.add(node);
		nodeLayer.repaint(); // TODO - Why?
		// TODO - if Promise is for a model, we have both id and label name.
		final Promise<String> idPromise = new Promise<>();
		final Future<String> futureId = idPromise.ask();
		node.setId(futureId);
		// TODO - do one thing only...
		eventBus.nodeCreated(new NodeCreationEvent(this, node, idPromise));
	}

	public void removeSelectedNodes() {
		// TODO - instead we could trigger the event below!
		for (final AbstractNode node : currentSelection) {
			eventBus.nodeRemoved(new NodeRemovalEvent(this, node));
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	public JLabel createLabel(final Point origin, final String nodeId) {
		// TODO - We could create an edge that connects label with node, synced
		// similarly to the node.
		final NodeLabel label = new NodeLabel(nodeId);
		addLabelComponent(label, origin);
		// eventBus.fireLabelCreatedEvent(null);
		return label;
	}

	private void addLabelComponent(final NodeLabel label, final Point origin) {
		label.setBounds(new Rectangle(origin, label.getPreferredSize()));
		labelLayer.add(label);
	}

	public EdgeComponent createEdge(final AbstractNode sourceNode, final Point target) {
		final Point source = getCenter(sourceNode);
		final EdgeComponent edge = new EdgeComponent(sourceNode, source, target);
		edge.setBounds(this.getBounds()); // OBSOLETE?
		edgeLayer.add(edge);
		return edge;
	}

	public void removeEdge(final EdgeComponent edge) {
		nodeLayer.remove(edge);
		repaint();
	}

	private class NodeCreator extends MouseAdapter {

		NodeCreator() {
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			if (hasState(State.MULTISELECTION)) {
				// User must click twice (first, to deselect, secondly to create
				// node)
				// TODO - we could also introduce another state
				// MULTISELECTION_CANCELLED
				// It would be nice to have a tri-state enum.
				nodeSelectionDragListener.cancel();
				return;
			}
			// TODO - this could go through the event bus.
			// Should the node creation listener itself have a state and listen
			// to events? Or should components toggle listeners depending on
			// state? That would result in a mapping of enum sets of states to
			// listener configurations. Those configurations can be verified by
			// partitioning the power set and mapping the partitions to listener
			// configurations. However, as power sets are too large we would
			// have to compute the conditions manually as if-else. We could use
			// the results to create documentation, though, and to test! Such
			// that: For a given configuration of states we expect a list of
			// activated listeners.
			final Point point = e.getPoint();
			if (hasState(State.TRANSITION_CREATION)) {
				createTransition(point);
			} else if (hasState(State.PLACE_CREATION)) {
				createPlace(point);
			} else {
				throw new IllegalStateException();
			}
			// TODO - components may dispatch action events directly:
			// dispatchEvent(new FooEvent(this, ActionEvent.ACTION_PERFORMED));
		}
	}

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
		nodeSelectionDragListener = new SelectionDragDropListener(currentSelection);
		for (final AbstractNode node : currentSelection) {
			node.setDragDropListener(nodeSelectionDragListener);
		}
	}

	/**
	 * Event source may be gridPanel or another listener, model etc.
	 */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// NOTE - How nodes are displayed is handled by the nodes
		// themselves.
		currentSelection.removeAll(event.getNodes());
		if (currentSelection.isEmpty()) {
			// We dont want to use property change listeners because we need to
			// connect references explicitly, adding listeners to all event sources.
			removeState(State.MULTISELECTION);
		}
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// TODO - this is somewhat inconsistent as we create labels in response
		// to node creation, Whereas from a data model point of view they are
		// treated as one. This was a requirement for id retrieval (threads).
		// The node creation request must be fired first in order to create an
		// id (by model). Only afterward can we create a label.
		if (e.getSource() != this) {
			// TODO
			throw new RuntimeException("TODO");
		} // else { // Node was created and added to panel, already. }
		final AbstractNode node = e.getNode();
		final Point labelOrigin = node.getLocation();
		labelOrigin.translate(0, -labelHeight);
		createLabel(labelOrigin, node.getId());
	}

	public void toggleNodeCreationMode() {
		// TODO - toggleCreationMode(); nicer!
		if (hasState(State.PLACE_CREATION)) {
			removeState(State.PLACE_CREATION);
			addState(State.TRANSITION_CREATION);
		} else if (hasState(State.TRANSITION_CREATION)) {
			removeState(State.TRANSITION_CREATION);
			addState(State.PLACE_CREATION);
		} else {
			throw new IllegalStateException();
		}
		// Preferred size changes, but should our snapTargetComponent
		// adapt dynamically to such changes?
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		// NOTE - Nodes, labels, and edges remove themselves.
		repaint();
	}
}
