package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.markusrother.swing.DragListener;
import de.markusrother.swing.HoverListener;
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
class PnGridPanel extends JPanel {

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	private static final Dimension placeDimensions = new Dimension(50, 50);

	private final SnapGridComponent snapGrid;
	private final MouseAdapter edgeEditListener;
	private final MouseAdapter nodeCreationListener;
	private final DragListener nodeSelectionListener;
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
		edgeEditListener = new EdgeEditListener();
		nodeCreationListener = new NodeCreationListener();
		nodeSelectionListener = new NodeSelectionListener();
		add(snapGrid);
		snapGrid.addMouseListener(nodeCreationListener);
		snapGrid.addMouseMotionListener(edgeEditListener);
		DragListener.addToComponent(snapGrid, nodeSelectionListener);
		//
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
		snapGrid.createSnapTarget(toggleBtn, new Point(100, 100));
	}

	private void addEdgeCreationListener(final JComponent component) {
		component.addMouseListener(edgeEditListener);
		// component.addMouseMotionListener(edgeEditListener);
	}

	private void addPlaceEditListener(final Place place) {
		// TODO - This belongs to place and could then be broadcasted on
		// the general message bus.
		place.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					final Place place = (Place) e.getComponent();
					// TODO - First talk to the model, then to the PnGrid!
					place.setMarking("foo");
				}
			}
		});
		HoverListener.addToComponent(place, NodeHoverListener.INSTANCE);
	}

	private void addTransitionEditListener(final Transition transition) {
		//
		transition.addMouseListener(new MouseAdapter() {

		});
		HoverListener.addToComponent(transition, NodeHoverListener.INSTANCE);
	}

	Point getGridRelativeLocation(final Point pointOnScreen) {
		return delta(pointOnScreen, snapGrid.getLocationOnScreen());
	}

	public boolean getState() {
		return state;
	}

	public Place createPlace(final Point point) {
		final Place place = new Place(placeDimensions);
		final int w = place.getWidth(); // getPreferredSize()
		final int h = place.getHeight();
		final Point topLeft = point.getLocation();
		topLeft.translate(-w / 2, -h / 2);
		snapGrid.createSnapTarget(place, topLeft);
		addEdgeCreationListener(place);
		addPlaceEditListener(place);
		return place;
	}

	public Transition createTransition(final Point point) {
		final Transition transition = new Transition("foo", transitionDimensions);
		final int w = transition.getWidth(); // getPreferredSize()
		final int h = transition.getHeight();
		final Point topLeft = point.getLocation();
		topLeft.translate(-w / 2, -h / 2);
		snapGrid.createSnapTarget(transition, topLeft);
		addEdgeCreationListener(transition);
		addTransitionEditListener(transition);
		return transition;
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
			edge.addMouseMotionListener(edgeEditListener);
			edge.addMouseListener(nodeCreationListener);
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
			final Point point = e.getPoint();
			if (getState()) {
				createTransition(point);
			} else {
				createPlace(point);
			}
			dispatchEvent(new FooEvent(this, 12345)); // TODO
		}
	}

	@Override
	protected void processEvent(final AWTEvent e) {
		super.processEvent(e);
		// System.out.println(e.getClass());
		// TODO - Then, if event is of given type, iterate over listeners
	}

}
