package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A mouse listener wrapper encapsulating drag events.
 * 
 * TODO - base deltas on componentMoved instead of mouse coordinates!
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class DragDropListener<T extends Component> extends MouseAdapter {

	/**
	 * <p>
	 * addToComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param listener
	 *            a {@link de.markusrother.swing.DragDropListener} object.
	 */
	public static void addToComponent(final Component component, final DragDropListener<?> listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	/**
	 * <p>
	 * removeFromComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param listener
	 *            a {@link de.markusrother.swing.DragDropListener} object.
	 */
	public static void removeFromComponent(final Component component, final DragDropListener<?> listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	private final Class<T> type;

	private Point dragStart;
	private boolean started; // OBSOLETE

	/**
	 * <p>
	 * Constructor for DragDropListener.
	 * </p>
	 *
	 * @param type
	 *            a {@link java.lang.Class} object.
	 */
	protected DragDropListener(final Class<T> type) {
		this.type = type;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(final MouseEvent e) {
		dragStart = dragStart != null ? dragStart : e.getLocationOnScreen();
		if (!started) {
			startDrag(tryCastComponent(e.getComponent()), e.getPoint());
			started = true;
		}
		final Point current = e.getLocationOnScreen();
		final int deltaX = current.x - dragStart.x;
		final int deltaY = current.y - dragStart.y;
		dragStart.translate(deltaX, deltaY);
		onDrag(tryCastComponent(e.getComponent()), deltaX, deltaY);
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(final MouseEvent e) {
		dragStart = e.getLocationOnScreen();
		// Resetting flag here, because this event mostly precedes
		// mouseDragged(). However, it does NOT always precede! Assume the
		// mousePressed event is caught by a component which is on top of this,
		// and the event does not bubble. TODO, TEST!
		started = false;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(final MouseEvent e) {
		dragStart = null;
		if (started) {
			endDrag(tryCastComponent(e.getComponent()), e.getPoint());
		}
	}

	/**
	 * <p>
	 * tryCastComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a T object.
	 */
	private T tryCastComponent(final Component component) {
		if (type.isInstance(component)) {
			return type.cast(component);
		} else {
			// TODO - what exception type?
			throw new IllegalArgumentException();
		}
	}

	/**
	 * <p>
	 * startDrag.
	 * </p>
	 *
	 * @param component
	 *            a T object.
	 * @param dragStart
	 *            in component
	 */
	public abstract void startDrag(T component, Point dragStart);

	/**
	 * <p>
	 * endDrag.
	 * </p>
	 *
	 * @param component
	 *            a T object.
	 * @param dragEnd
	 *            in component
	 */
	public abstract void endDrag(T component, Point dragEnd);

	/**
	 * No deltas are lost!
	 *
	 * @param component
	 *            a T object.
	 * @param deltaX
	 *            a int.
	 * @param deltaY
	 *            a int.
	 */
	public abstract void onDrag(T component, int deltaX, int deltaY);

}
