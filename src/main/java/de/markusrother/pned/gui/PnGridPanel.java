package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.NodeSelectionEvent.Type.UNSELECTED;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
class PnGridPanel extends JPanel implements NodeSelectionListener, NodeCreationListener {

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	private static final Dimension placeDimensions = new Dimension(50, 50);

	static EventBus eventBus;

	private final JComponent snapGrid;
	private final MouseAdapter edgeEditListener;
	private final MouseAdapter nodeCreationListener;
	private final DragDropListener nodeSelectionListener;
	private boolean state;

	public static Point delta(final Point a, final Point b) {
		return new Point(a.x - b.x, a.y - b.y);
	}

	/**
	 * I don't quite like passing this to other classes/methods/constructors,
	 * while this is not fully initialized!
	 */
	PnGridPanel() {
		// TODO - add scroll panel
		setPreferredSize(preferredSize);
		// setBackground(Color.BLUE);
		// TODO - set the number of rectangles to be displayed:
		snapGrid = new SnapGridComponent(new Dimension(40, 40), Color.GRAY);
		// TODO - get preferred size from parent
		snapGrid.setPreferredSize(preferredSize);

		// Listeners that are needed by children, are kept here:
		edgeEditListener = new EdgeEditListener();
		nodeCreationListener = new NodeCreationListener();
		nodeSelectionListener = new NodeSelector();

		add(snapGrid);
		snapGrid.addMouseListener(nodeCreationListener);
		snapGrid.addMouseMotionListener(edgeEditListener);
		DragDropListener.addToComponent(snapGrid, nodeSelectionListener);

		// Adding state type toggle
		final PnGridPanel that = this;
		final JButton toggleBtn = new JButton("place");
		toggleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				that.state = !that.state;
				toggleBtn.setText(that.state ? "transition" : "place");
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
		component.addMouseListener(edgeEditListener);
		// component.addMouseMotionListener(edgeEditListener);
	}

	Point getGridRelativeLocation(final Point pointOnScreen) {
		return delta(pointOnScreen, snapGrid.getLocationOnScreen());
	}

	public boolean getState() {
		return state;
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

	public <T extends AbstractNode> void addNodeComponent(final T node, final Point point) {
		final Dimension d = node.getPreferredSize();
		final Point topLeft = point.getLocation(); // TODO - Why?
		topLeft.translate(-d.width / 2, -d.height / 2); // TODO - Why?
		node.setBounds(new Rectangle(topLeft, node.getPreferredSize()));
		snapGrid.add(node);
		snapGrid.repaint(); // TODO - Why?
		// TODO - if Promise is for a model, we have both id and label name.
		final Promise<String> idPromise = new Promise<>();
		final Future<String> future = idPromise.get();
		node.setId(future);
		createLabel(node, "test", future);
		eventBus.nodeCreated(new NodeCreationEvent(this, node, idPromise));
	}

	public JLabel createLabel(final AbstractNode node, final String name, final Future<String> futureNodeId) {
		// TODO - Get rid of node argument!
		// TODO - We could create an edge that connects label with node, synced
		// similarly to the node.
		// TODO - Extract class!
		final JLabel label = new JLabel(name);
		final Rectangle r = new Rectangle(node.getLocation(), label.getPreferredSize());
		r.translate(0, -label.getPreferredSize().height);
		label.setBounds(r);
		snapGrid.add(label);
		eventBus.addNodeMotionListener(new NodeMotionListener() {

			@Override
			public void nodeMoved(final NodeMovedEvent event) {
				if (event.getNodes().contains(node)) {
					final Rectangle r = label.getBounds();
					r.translate(event.getDeltaX(), event.getDeltaY());
					System.out.println(event.getDeltaX() + " " + event.getDeltaY());
					label.setBounds(r);
					label.repaint();
				}
			}
		});
		return label;
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
		snapGrid.add(edge);
		return edge;
	}

	void removeEdge(final EdgeComponent edge) {
		snapGrid.remove(edge);
		repaint();
	}

	/**
	 * TODO - split into Creation and edit listener!
	 */
	private class EdgeEditListener extends MouseAdapter {

		// TODO - Drawing could also start upon exit!

		private EdgeComponent edge;

		EdgeEditListener() {
		}

		private void startNewEdge(final AbstractNode source, final Point point) {
			// TODO - nicer (should not call surrounding class):
			edge = createEdge(source, point);
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

		@Override
		public void mouseClicked(final MouseEvent e) {
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			if (edge != null) {
				// Connecting existing edge:
				// TODO - edge should never have invalid targetComponent!
				if (edge.acceptsTarget(e.getComponent())) {
					final AbstractNode targetNode = (AbstractNode) e.getComponent();
					finishCurrentEdge(targetNode);
				} else {
					// TODO - nicer (should not call surrounding class):
					// The edge is not yet part of the model and could go to a
					// different layer!
					removeEdge(edge);
					// container.removeEdge(edge);
				}
				edge = null;
			} else {
				final AbstractNode sourceNode = (AbstractNode) e.getComponent();
				final Point point = getGridRelativeLocation(e.getLocationOnScreen());
				startNewEdge(sourceNode, point);
			}
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (edge != null) {
				edge.setTarget(getGridRelativeLocation(e.getLocationOnScreen()));
				// TODO - This causes flickering, whereas the solution with
				// drawing from the center is more pleasing but does not work
				// with transparent elements. The cleanest solution would be to
				// manage the edge points at the edge component, too. Then we
				// can determine the drawing direction more precisely without
				// the flickering.
				// edge.connectToSource(edge.getSourceComponent());
				repaint();
			}
		}

		@Override
		public void mouseEntered(final MouseEvent e) {
			if (edge != null) {
				final Component possibleTarget = e.getComponent();
				if (edge.acceptsTarget(possibleTarget)) {
					edge.connectToTarget((AbstractNode) possibleTarget);
					edge.highlightValid();
				} else {
					edge.highlightInvalid();
				}
			}
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			if (edge != null) {
				edge.highlightStandard();
			}
		}

	}

	private class NodeCreationListener extends MouseAdapter {

		NodeCreationListener() {
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			// TODO - this could go through the event bus!
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
			if (getState()) {
				createTransition(point);
			} else {
				createPlace(point);
			}
			// TODO - components may dispatch action events directly:
			// dispatchEvent(new FooEvent(this, ActionEvent.ACTION_PERFORMED));
		}
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		final List<AbstractNode> nodes = event.getNodes();
		// TODO - Extract MultiSelectionDragDropListener
		final DragDropListener dragListener = new DragDropListener() {

			Point absStart;

			@Override
			public void startDrag(final Component component, final Point point) {
				absStart = component.getLocation();
			}

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				for (final AbstractNode node : nodes) {
					final Rectangle r = node.getBounds();
					r.translate(deltaX, deltaY);
					node.setBounds(r);
					node.repaint();
				}
			}

			@Override
			public void endDrag(final Component component, final Point dragEnd) {
				final Point absEnd = component.getLocation();
				for (final AbstractNode node : nodes) {
					DragDropListener.removeFromComponent(node, this);
				}
				final Point delta = delta(absEnd, absStart);
				eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(UNSELECTED, this, nodes));
				// TODO - after all, we may want to fire constantly...
				eventBus.fireNodeMovedEvent(new NodeMovedEvent(this, nodes, delta.x, delta.y));
			}

		};
		for (final AbstractNode node : nodes) {
			DragDropListener.addToComponent(node, dragListener);
		}
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// TODO - ignore only if source is self
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		if (e.getSource() == this) {
			// IGNORE - Node was created and added to panel, already.
			return;
		}
		// TODO
		throw new RuntimeException("TODO");
	}

}
