package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>
 * Abstract HoverListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class HoverListener extends MouseAdapter {

	/**
	 * <p>
	 * addToComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param listener
	 *            a {@link de.markusrother.swing.HoverListener} object.
	 */
	public static void addToComponent(final Component component, final HoverListener listener) {
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
	 *            a {@link de.markusrother.pned.gui.listeners.NodeHoverListener}
	 *            object.
	 */
	public static void removeFromComponent(final Component component, final HoverListener listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	private boolean hovering = false;

	/** {@inheritDoc} */
	@Override
	public void mouseExited(final MouseEvent e) {
		if (hovering) {
			hovering = false;
			endHover(e.getComponent());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(final MouseEvent e) {
		if (hovering) {
			if (!inHoverArea(e.getPoint())) {
				hovering = false;
				endHover(e.getComponent());
			}
		} else if (inHoverArea(e.getPoint())) {
			hovering = true;
			startHover(e.getComponent());
		}
	}

	// TODO - this should default to the component area. Either create another
	// constructor which takes a predicate or create a subclass where this
	// method is abstract. Alternatively, getHoverArea() -> Shape
	/**
	 * <p>
	 * inHoverArea.
	 * </p>
	 *
	 * @param p
	 *            a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	protected abstract boolean inHoverArea(Point p);

	/**
	 * <p>
	 * startHover.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 */
	protected abstract void startHover(Component component);

	/**
	 * <p>
	 * endHover.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 */
	protected abstract void endHover(Component component);

	/**
	 * <p>addToComponent.</p>
	 *
	 * @param component a {@link java.awt.Component} object.
	 */
	public void addToComponent(final Component component) {
		addToComponent(component, this);
	}

	/**
	 * <p>removeFromComponent.</p>
	 *
	 * @param component a {@link java.awt.Component} object.
	 */
	public void removeFromComponent(final Component component) {
		removeFromComponent(component, this);
	}

}
