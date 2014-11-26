package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO - make generic on component type!
public abstract class DragDropListener extends MouseAdapter {

	public static void addToComponent(final Component component, final DragDropListener listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public static void removeFromComponent(final Component component, final DragDropListener listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	private Point dragStart;
	private boolean started; // OBSOLETE

	@Override
	public void mouseDragged(final MouseEvent e) {
		dragStart = dragStart != null ? dragStart : e.getLocationOnScreen();
		if (!started) {
			startDrag(e.getComponent(), e.getPoint());
			started = true;
		}
		// NOTE - e.getPoint() is relative to this!
		// FIXME - stop at grid bounds!!!
		// TODO - make this sticky! Should stop at the grid's snapPoints.
		// TODO - components must not overlap!
		final Point current = e.getLocationOnScreen();
		final int deltaX = current.x - dragStart.x;
		final int deltaY = current.y - dragStart.y;
		dragStart.translate(deltaX, deltaY);
		onDrag(e.getComponent(), deltaX, deltaY);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		dragStart = e.getLocationOnScreen();
		// Resetting flag here, because this event always precedes
		// mouseDragged().
		// TODO - it does NOT always precede! Assume the mousePressed event is
		// caught by a component which is on top of this, and the event does not
		// bubble.
		started = false;
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		dragStart = null;
		if (started) {
			endDrag(e.getComponent(), e.getPoint());
		}
	}

	/**
	 * 
	 * @param component
	 * @param dragStart
	 *            in component
	 */
	public abstract void startDrag(Component component, Point dragStart);

	/**
	 * 
	 * @param component
	 * @param dragEnd
	 *            in component
	 */
	public abstract void endDrag(Component component, Point dragEnd);

	/**
	 * No deltas are lost!
	 * 
	 * @param component
	 * @param deltaX
	 * @param deltaY
	 */
	public abstract void onDrag(Component component, int deltaX, int deltaY);

}
