package de.markusrother.swing;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class DragListener extends MouseAdapter {

	private Point point;

	@Override
	public void mouseDragged(final MouseEvent e) {
		// NOTE - e.getPoint() is relative to this!
		// FIXME - stop at grid bounds!!!
		// TODO - make this sticky! Should stop at the grid's snapPoints.
		// TODO - components must not overlap!
		final Point current = e.getLocationOnScreen();
		final int deltaX = current.x - point.x;
		final int deltaY = current.y - point.y;
		point.translate(deltaX, deltaY);
		onDrag(deltaX, deltaY);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		point = e.getLocationOnScreen();
	}

	public abstract void onDrag(int deltaX, int deltaY);

}
