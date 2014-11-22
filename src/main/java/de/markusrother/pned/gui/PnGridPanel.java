package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.markusrother.concurrent.Promise;
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
class PnGridPanel extends JLayeredPane implements NodeSelectionListener, NodeListener {

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

	static EventBus eventBus;

	private final JComponent snapGrid;
	private final MouseAdapter edgeCreationListener;
	private final MouseAdapter nodeCreationListener;
	private final NodeSelector nodeSelectionListener;
	private final PnGridPopupListener popupListener;
	private final EnumSet<State> state;
	private final JComponent edgeLayer;
	// Stateful/Throwaway listeners:
	SelectionDragDropListener selectionDragListener;

	private final List<AbstractNode> currentSelection;

	public static Point delta(final Point a, final Point b) {
		return new Point(a.x - b.x, a.y - b.y);
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
		snapGrid = new SnapGridComponent(new Dimension(40, 40), Color.GRAY);
		// TODO - get preferred size from parent
		snapGrid.setPreferredSize(preferredSize);
		// Must set bounds manually for JLayeredPane
		snapGrid.setBounds(new Rectangle(new Point(0, 0), preferredSize));

		edgeLayer = new JComponent() {
			// TODO - For some strange reason we cannot add JPanels to
			// JLayeredPane
		};
		// Must set bounds manually for JLayeredPane
		edgeLayer.setBounds(new Rectangle(new Point(0, 0), preferredSize));
		edgeLayer.setBackground(Color.LIGHT_GRAY);
		add(edgeLayer, new Integer(0));

		// new ListenerManager(State) {
		// List<EventListener> getListeners(EnumSet<State> enumSet) {
		// if...else
		// }
		// }
		// Listeners that are needed by children, are kept here:
		edgeCreationListener = new EdgeCreationListener(this);
		nodeCreationListener = new NodeCreationListener();
		nodeSelectionListener = new NodeSelector();
		popupListener = new PnGridPopupListener(this);

		currentSelection = new LinkedList<>();

		add(snapGrid, new Integer(1));
		snapGrid.addMouseListener(nodeCreationListener);
		snapGrid.addMouseMotionListener(edgeCreationListener);
		snapGrid.addMouseListener(popupListener);
		DragDropListener.addToComponent(snapGrid, nodeSelectionListener);

		// TODO - Make this a lazy initialized singleton! First try if a static
		// singleton works, too
		eventBus = new EventBus();
		eventBus.addNodeSelectionListener(this);
		eventBus.addNodeListener(this);
	}

	private void addEdgeCreationListenerTo(final JComponent component) {
		component.addMouseListener(edgeCreationListener);
		// component.addMouseMotionListener(edgeEditListener);
	}

	Point getGridRelativeLocation(final Point pointOnScreen) {
		return delta(pointOnScreen, snapGrid.getLocationOnScreen());
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
		addEdgeCreationListenerTo(place);
		return place;
	}

	public Transition createTransition(final Point point) {
		final Transition transition = new Transition(transitionDimensions);
		addNodeComponent(transition, point);
		addEdgeCreationListenerTo(transition);
		return transition;
	}

	private <T extends AbstractNode> void addNodeComponent(final T node, final Point origin) {
		// TODO - this method is too big!
		final Dimension d = node.getPreferredSize();
		final Point nodeOrigin = origin.getLocation(); // TODO - Why?
		nodeOrigin.translate(-d.width / 2, -d.height / 2); // TODO - Why?
		node.setBounds(new Rectangle(nodeOrigin, node.getPreferredSize()));
		snapGrid.add(node);
		snapGrid.repaint(); // TODO - Why?
		// TODO - if Promise is for a model, we have both id and label name.
		final Promise<String> idPromise = new Promise<>();
		final Future<String> futureId = idPromise.ask();
		node.setId(futureId);
		// TODO - do one thing only...
		eventBus.nodeCreated(new NodeCreationEvent(this, node, idPromise));
	}

	public void removeSelectedNodes() {
		for (final AbstractNode node : currentSelection) {
			eventBus.nodeRemoved(new NodeRemovalEvent(this, node));
		}
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
		snapGrid.add(label);
	}

	public EdgeComponent createEdge(final AbstractNode sourceNode, final Point target) {
		final Point source = sourceNode.getCenter();
		final EdgeComponent edge = new EdgeComponent(sourceNode, source, target);
		edge.setBounds(this.getBounds()); // OBSOLETE?
		edgeLayer.add(edge);
		return edge;
	}

	void removeEdge(final EdgeComponent edge) {
		snapGrid.remove(edge);
		repaint();
	}

	private class NodeCreationListener extends MouseAdapter {

		NodeCreationListener() {
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			if (hasState(State.MULTISELECTION)) {
				// User must click twice (first, to deselect, secondly to create
				// node)
				// TODO - we could also introduce another state
				// MULTISELECTION_CANCELLED
				// It would be nice to have a tri-state enum.
				selectionDragListener.cancel();
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
		addState(State.MULTISELECTION);
		currentSelection.addAll(event.getNodes());
		// TODO - Extract MultiSelectionDragDropListener
		selectionDragListener = new SelectionDragDropListener(currentSelection);
		SelectionDragDropListener.addToComponents(currentSelection, selectionDragListener);
	}

	/**
	 * Event source may be gridPanel or another listener, model etc.
	 */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// NOTE - How nodes are displayed is handled by the nodes
		// themselves.
		// removeState(State.MULTISELECTION); // TEMP
		currentSelection.removeAll(event.getNodes());
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		if (e.getSource() != this) {
			// TODO
			throw new RuntimeException("TODO");
		} // else { // Node was created and added to panel, already. }
		final AbstractNode node = e.getNode();
		final Point labelOrigin = node.getLocation();
		labelOrigin.translate(0, -labelHeight);
		createLabel(labelOrigin, node.getId());
	}

	void toggleNodeCreationMode() {
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
		// TODO - also the node itself should be removed, as it may still be
		// referenced somewhere, and may have listeners.
		snapGrid.remove(e.getNode());
		snapGrid.repaint();
	}
}
