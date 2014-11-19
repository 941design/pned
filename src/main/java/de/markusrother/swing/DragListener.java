package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class DragListener extends MouseAdapter {

	public static void addToComponent(final Component component, final DragListener listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	private Point point;
	private boolean started;

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (!started) {
			startDrag(e.getComponent(), e.getPoint());
			started = true;
		}
		// NOTE - e.getPoint() is relative to this!
		// FIXME - stop at grid bounds!!!
		// TODO - make this sticky! Should stop at the grid's snapPoints.
		// TODO - components must not overlap!
		final Point current = e.getLocationOnScreen();
		final int deltaX = current.x - point.x;
		final int deltaY = current.y - point.y;
		point.translate(deltaX, deltaY);
		onDrag(e.getComponent(), deltaX, deltaY);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		point = e.getLocationOnScreen();
		// Resetting flag here, because this event always preceedes
		// mouseDragged().
		started = false;
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		point = null;
		if (started) {
			endDrag(e.getComponent(), e.getPoint());
		}
	}

	public abstract void startDrag(Component component, Point point);

	public abstract void endDrag(Component component, Point point);

	/**
	 * No deltas are lost!
	 * 
	 * @param component
	 * @param deltaX
	 * @param deltaY
	 */
	public abstract void onDrag(Component component, int deltaX, int deltaY);

}
