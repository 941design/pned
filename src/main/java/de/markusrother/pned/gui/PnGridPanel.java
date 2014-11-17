package de.markusrother.pned.gui;

import static de.markusrother.swing.snap.SnapLayoutManager.EAST;
import static de.markusrother.swing.snap.SnapLayoutManager.NORTH;
import static de.markusrother.swing.snap.SnapLayoutManager.SOUTH;
import static de.markusrother.swing.snap.SnapLayoutManager.WEST;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.markusrother.swing.snap.SnapGridComponent;
import de.markusrother.swing.snap.SnapPoint;
import de.markusrother.swing.snap.SnapTarget;

/**
 * TODO - use MouseAdapter!
 * 
 * TODO - public method to add nodes at logical locations?
 */
class PnGridPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension transitionDimensions = new Dimension(50, 50);
	private static final Dimension placeDimensions = new Dimension(50, 50);

	private final SnapGridComponent snapGrid;
	private EdgeComponent edgeComponent;
	protected boolean toggle;

	PnGridPanel() {
		// TODO - add scroll panel
		setPreferredSize(preferredSize);
		// setBackground(Color.BLUE);
		// TODO - set the number of rectangles to be displayed:
		snapGrid = new SnapGridComponent(new Dimension(40, 40), Color.GRAY);
		// TODO - get preferred size from parent
		snapGrid.setPreferredSize(preferredSize);
		snapGrid.addMouseListener(this);
		snapGrid.addMouseMotionListener(this);
		add(snapGrid);
		//
		// Adding state type toggle
		final PnGridPanel that = this;
		final JButton toggleBtn = new JButton("place");
		toggleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				that.toggle = !that.toggle;
				toggleBtn.setText(that.toggle ? "transition" : "place");
				// Preferred size changes, but should our snapTargetComponent
				// adapt dynamically to such changes?
				// System.out.println(toggleBtn.getPreferredSize());
			}
		});
		snapGrid.createSnapTarget(toggleBtn, new Point(100, 100));
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		final Point coordinate = snapGrid.getCurrentSnapPoint();
		// TODO - adding component should be done by the listener - not
		// necessarily this class. Factor out listener!
		if (toggle) {
			final Transition transition = new Transition("foo", transitionDimensions);
			addTransition(transition, coordinate);
		} else {
			final Place place = new Place(placeDimensions);
			addPlace(place, coordinate);
		}
	}

	private void addPlace(final Place place, final Point coordinate) {
		final int w = place.getWidth(); // getPreferredSize()
		final int h = place.getHeight();
		final Point topLeft = coordinate.getLocation();
		topLeft.translate(-w / 2, -h / 2);
		// node.setBounds(new Rectangle(topLeft, node.getPreferredSize()));
		// TODO - use LayoutManager for this?
		// TODO - constraint CENTERED, or corner/snapPoint
		// TODO - Subclass SnapTarget to add anonymous snapPoints with a
		// getPreferredBounds(Component c) method.
		final SnapTarget snapTarget = snapGrid.createSnapTarget(place, topLeft);
		snapGrid.createSnapPoint(snapTarget, NORTH);
		snapGrid.createSnapPoint(snapTarget, SOUTH);
		snapGrid.createSnapPoint(snapTarget, EAST);
		snapGrid.createSnapPoint(snapTarget, WEST);
		snapTarget.showSnapPoints();
	}

	private void addTransition(final Transition transition, final Point coordinate) {
		final int w = transition.getWidth(); // getPreferredSize()
		final int h = transition.getHeight();
		final Point topLeft = coordinate.getLocation();
		topLeft.translate(-w / 2, -h / 2);
		// node.setBounds(new Rectangle(topLeft, node.getPreferredSize()));
		// TODO - use LayoutManager for this?
		// TODO - constraint CENTERED, or corner/snapPoint
		// TODO - Subclass SnapTarget to add anonymous snapPoints with a
		// getPreferredBounds(Component c) method.
		final SnapTarget snapTarget = snapGrid.createSnapTarget(transition, topLeft);
		snapGrid.createSnapPoint(snapTarget, NORTH);
		snapGrid.createSnapPoint(snapTarget, SOUTH);
		snapGrid.createSnapPoint(snapTarget, EAST);
		snapGrid.createSnapPoint(snapTarget, WEST);
		snapTarget.showSnapPoints();
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (hasEdgeComponent()) {
			// TODO - If edge target is a node,
			if (edgeComponent.targetIsNode() && edgeComponent.length() > 0) {
				edgeComponent.finishedDrawing();
				edgeComponent = null;
			} else {
				removeEdgeComponent();
			}
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		// TODO - change this behavior to a click to start click to end instead
		// of a drag!
		// TODO - highlight is lost when dragging out of bounds.
		if (!hasEdgeComponent()) {
			// Edge is created once we know this is a drag operation!
			// findNearestGridSnapPoint(e.getPoint())
			addEdgeComponent(snapGrid.getCurrentSnapPoint());
		}
		// TODO - check if there is a snap target within a given tolerance. If
		// so, then snap to that point.
		moveEdgeTip(e.getPoint());
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		// IGNORE
	}

	private boolean hasEdgeComponent() {
		return edgeComponent != null;
	}

	private void addEdgeComponent(final SnapPoint tail) {
		edgeComponent = new EdgeComponent(tail);
		edgeComponent.setBounds(0, 0, getWidth(), getHeight());
		snapGrid.add(edgeComponent);
	}

	private void removeEdgeComponent() {
		snapGrid.remove(edgeComponent);
		repaint();
		edgeComponent = null;
	}

	private void moveEdgeTip(final Point head) {
		// TODO - currently, this causes a leak, because snapPoints in grid are
		// created lazily! (This was a case for premature optimization)!
		// TODO - We want to add a tolerance parameter to findNearestSnapTarget
		final SnapPoint snapTarget = snapGrid.findNearestSnapTarget(head);
		edgeComponent.setTarget(snapTarget);
		repaint();
	}

}
