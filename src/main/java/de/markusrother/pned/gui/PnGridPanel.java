package de.markusrother.pned.gui;

import static de.markusrother.swing.snap.SnapLayoutManager.EAST;
import static de.markusrother.swing.snap.SnapLayoutManager.NORTH;
import static de.markusrother.swing.snap.SnapLayoutManager.SOUTH;
import static de.markusrother.swing.snap.SnapLayoutManager.WEST;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import de.markusrother.swing.snap.SnapGridComponent;
import de.markusrother.swing.snap.SnapPoint;
import de.markusrother.swing.snap.SnapTarget;

/**
 * TODO - use MouseAdapter!
 */
class PnGridPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final Dimension preferredSize = new Dimension(500, 500);
	private static final Dimension nodeDimensions = new Dimension(50, 50);

	private final SnapGridComponent snapGrid;
	private EdgeComponent edgeComponent;

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
	}

	/**
	 * logical location
	 * 
	 * @param i
	 * @param j
	 */
	public void addNode(final int i, final int j) {
		// TODO - what if position is taken?
		final NodeComponent nodeComponent = new NodeComponent("node", nodeDimensions);
		// TODO - use layout manager to manage preferred sizes!
		nodeComponent.setPreferredSize(nodeDimensions);
		add(nodeComponent);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		final Point coordinate = snapGrid.getCurrentSnapPoint();
		final NodeComponent node = new NodeComponent("foo", nodeDimensions);
		// TODO - adding component should be done by the listener. This
		// class should then be responsible to add the component at the last
		// recorded
		// location.
		addNodeComponent(node, coordinate);
	}

	private void addNodeComponent(final NodeComponent node, final Point point) {
		// at grid point
		final int x = (int) point.getX();
		final int y = (int) point.getY();
		final int w = node.getWidth(); // getPreferredSize()
		final int h = node.getHeight();
		final Point topLeft = point.getLocation();
		topLeft.translate(-w / 2, -h / 2);
		// node.setBounds(new Rectangle(topLeft, node.getPreferredSize()));
		// TODO - use LayoutManager for this?
		// TODO - constraint CENTERED, or corner/snapPoint
		// TODO - Subclass SnapTarget to add anonymous snapPoints with a
		// getPreferredBounds(Component c) method.
		final SnapTarget snapTarget = snapGrid.addSnapTarget(node, topLeft);
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
