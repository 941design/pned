package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.JButton;
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
class PnGridPanel extends JLayeredPane implements NodeSelectionListener, NodeCreationListener {

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
	private final DragDropListener nodeSelectionListener;
	private final EnumSet<State> state;
	private final JComponent edgeLayer;
	// Stateful/Throwaway listeners:
	SelectionDragDropListener selectionDragListener;

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

		add(snapGrid, new Integer(1));
		snapGrid.addMouseListener(nodeCreationListener);
		snapGrid.addMouseMotionListener(edgeCreationListener);
		DragDropListener.addToComponent(snapGrid, nodeSelectionListener);

		// Adding state type toggle
		final PnGridPanel that = this;
		final JButton toggleBtn = new JButton("place");
		toggleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
                // TODO - toggleCreationMode(); nicer!
				if (that.hasState(State.PLACE_CREATION)) {
					that.removeState(State.PLACE_CREATION);
					that.addState(State.TRANSITION_CREATION);
					toggleBtn.setText("transition");
				} else if (that.hasState(State.TRANSITION_CREATION)) {
					that.removeState(State.TRANSITION_CREATION);
					that.addState(State.PLACE_CREATION);
					toggleBtn.setText("place");
				} else {
					throw new IllegalStateException();
				}
				// Preferred size changes, but should our snapTargetComponent
				// adapt dynamically to such changes?
			}
		});
		snapGrid.add(toggleBtn);
		toggleBtn.setBounds(new Rectangle(new Point(100, 100), toggleBtn.getPreferredSize()));

		// TODO - Make this a lazy initialized singleton! First try if a static
		// singleton works, too
		eventBus = new EventBus();
		eventBus.addNodeSelectionListener(this);
		eventBus.addNodeCreationListener(this);
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

	private Point getCenter(final Component component) {
		final Point point = getGridRelativeLocation(component.getLocationOnScreen());
		point.translate(component.getWidth() / 2, component.getHeight() / 2);
		return point;
	}

	public EdgeComponent createEdge(final AbstractNode sourceComponent, final Point target) {
		final Point source = getCenter(sourceComponent);
		final EdgeComponent edge = new EdgeComponent(sourceComponent, source, target);
		edge.setBounds(0, 0, getWidth(), getHeight());
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
		addState(State.MULTISELECTION);
		final List<AbstractNode> nodes = event.getNodes();
		// TODO - Extract MultiSelectionDragDropListener
		selectionDragListener = new SelectionDragDropListener(nodes);
		SelectionDragDropListener.addToComponents(nodes, selectionDragListener);
	}

	/**
	 * Event source may be gridPanel or another listener, model etc.
	 */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// NOTE - How nodes are displayed is handled by the nodes
		// themselves.
		removeState(State.MULTISELECTION);
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
}
