package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>
 * A mouse listener wrapper encapsulating hover events, where the hover area are
 * the component bounds.
 * </p>
 * 
 * TODO - Call the implementor to check whether an event is in the components
 * intended hover area, such as isInHoverArea(MouseEvent e).
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
	 *            a
	 *            {@link de.markusrother.pned.gui.components.listeners.NodeHoverListener}
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
		final Component component = e.getComponent();
		if (hovering) {
			if (!inHoverArea(component, e.getPoint())) {
				hovering = false;
				endHover(component);
			}
		} else if (inHoverArea(component, e.getPoint())) {
			hovering = true;
			startHover(component);
		}
	}

	/**
	 * <p>
	 * inHoverArea.
	 * </p>
	 *
	 * @param p
	 *            a {@link java.awt.Point} object.
	 * @return a boolean.
	 * @param component
	 *            a {@link java.awt.Component} object.
	 */
	protected abstract boolean inHoverArea(Component component, Point p);

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
	 * <p>
	 * addToComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 */
	public void addToComponent(final Component component) {
		addToComponent(component, this);
	}

	/**
	 * <p>
	 * removeFromComponent.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 */
	public void removeFromComponent(final Component component) {
		removeFromComponent(component, this);
	}

}
