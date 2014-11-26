package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class DragDropListener<T extends Component> extends MouseAdapter {

	public static void addToComponent(final Component component, final DragDropListener<?> listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public static void removeFromComponent(final Component component, final DragDropListener<?> listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	private final Class<T> type;

	private Point dragStart;
	private boolean started; // OBSOLETE

	protected DragDropListener(final Class<T> type) {
		this.type = type;
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		dragStart = dragStart != null ? dragStart : e.getLocationOnScreen();
		if (!started) {
			startDrag(tryCastComponent(e.getComponent()), e.getPoint());
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
		onDrag(tryCastComponent(e.getComponent()), deltaX, deltaY);
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
			endDrag(tryCastComponent(e.getComponent()), e.getPoint());
		}
	}

	private T tryCastComponent(final Component component) {
		if (type.isInstance(component)) {
			return type.cast(component);
		} else {
			// TODO - what exception type?
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * @param component
	 * @param dragStart
	 *            in component
	 */
	public abstract void startDrag(T component, Point dragStart);

	/**
	 * 
	 * @param component
	 * @param dragEnd
	 *            in component
	 */
	public abstract void endDrag(T component, Point dragEnd);

	/**
	 * No deltas are lost!
	 * 
	 * @param component
	 * @param deltaX
	 * @param deltaY
	 */
	public abstract void onDrag(T component, int deltaX, int deltaY);

}
