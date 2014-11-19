package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.markusrother.pned.gui.NodeHoverListener;

public abstract class HoverListener extends MouseAdapter {

	public static void addToComponent(final Component component, final HoverListener listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}

	public static void removeFromComponent(final Component component, final NodeHoverListener listener) {
		component.removeMouseListener(listener);
		component.removeMouseMotionListener(listener);
	}

	private boolean hovering = false;

	@Override
	public void mouseExited(final MouseEvent e) {
		if (hovering) {
			hovering = false;
			endHover(e.getComponent());
		}
	}

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
	protected abstract boolean inHoverArea(Point p);

	protected abstract void startHover(Component component);

	protected abstract void endHover(Component component);
}
