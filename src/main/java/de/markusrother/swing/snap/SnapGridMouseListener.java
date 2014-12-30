package de.markusrother.swing.snap;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Needed to listen to snap points of different components.
 *
 * TODO - Create different listeners: One for listening to all focus events, and
 * another for listening to the grid exit event only!
 *
 * @author Markus Rother
 * @version 1.0
 */
class SnapGridMouseListener extends MouseAdapter {

	private final SnapGridComponent grid;

	/**
	 * <p>Constructor for SnapGridMouseListener.</p>
	 *
	 * @param grid a {@link de.markusrother.swing.snap.SnapGridComponent} object.
	 */
	public SnapGridMouseListener(final SnapGridComponent grid) {
		this.grid = grid;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final MouseEvent e) {
		final Point point = e.getPoint();
		if (e.getSource() == grid) {
			if (point.x < 0 //
					|| point.y < 0 //
					|| point.x > grid.getWidth() //
					|| point.y > grid.getHeight()) {
				grid.disableCurrentSnapPoint();
			}
		} else {
			// TODO - take focus distance from config.
			grid.toggleNearestSnapPoint(getRelativeToGrid(e));
		}
	}

	/**
	 * <p>getRelativeToGrid.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 * @return a {@link java.awt.Point} object.
	 */
	private Point getRelativeToGrid(final MouseEvent e) {
		final Point point = e.getPoint();
		final Point componentLocation = e.getComponent().getLocationOnScreen();
		final Point gridLocation = grid.getLocationOnScreen();
		point.translate(componentLocation.x, componentLocation.y);
		point.translate(-gridLocation.x, -gridLocation.y);
		return point;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(final MouseEvent e) {
		final Point point = e.getSource() == grid ? e.getPoint() : getRelativeToGrid(e);
		grid.toggleNearestSnapPoint(point);
	}
}
