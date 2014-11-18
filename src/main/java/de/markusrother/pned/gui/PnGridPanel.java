package de.markusrother.pned.gui;

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

import de.markusrother.swing.snap.SnapGridComponent;

/**
 * TODO - use MouseAdapter!
 * 
 * TODO - public method to add nodes at logical locations?
 */
class PnGridPanel extends JPanel {

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	private static final Dimension placeDimensions = new Dimension(50, 50);

	private final SnapGridComponent snapGrid;
	private final MouseAdapter edgeEditListener;
	private final MouseAdapter nodeCreationListener;

	private boolean state;

	public static Point delta(final Point a, final Point b) {
		return new Point(a.x - b.x, a.y - b.y);
	}

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
		add(snapGrid);
		snapGrid.addMouseListener(nodeCreationListener);
		snapGrid.addMouseMotionListener(edgeEditListener);
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

	private class EdgeEditListener extends MouseAdapter {

		// TODO - Drawing could also start upon exit!

		private EdgeComponent edge;

		EdgeEditListener() {
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			if (edge != null) {
				// TODO - edge should never have invalid targetComponent!
				if (edge.acceptsTarget(e.getComponent())) {
					edge.setTargetComponent((AbstractNode) e.getComponent());
					edge.finishedDrawing();
				} else {
					removeEdge(edge);
				}
				edge = null;
			} else {
				edge = createEdge((AbstractNode) e.getComponent(), getGridRelativeLocation(e.getLocationOnScreen()));
			}
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (edge != null) {
				edge.setTarget(getGridRelativeLocation(e.getLocationOnScreen()));
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
		}

	}

}
