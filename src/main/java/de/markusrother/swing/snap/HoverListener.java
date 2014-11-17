package de.markusrother.swing.snap;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class HoverListener extends MouseAdapter {

	private boolean hovering = false;

	@Override
	public void mouseExited(final MouseEvent e) {
		if (hovering) {
			hovering = false;
			endHover();
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		if (hovering) {
			if (!inHoverArea(e.getPoint())) {
				hovering = false;
				endHover();
			}
		} else if (inHoverArea(e.getPoint())) {
			hovering = true;
			startHover();
		}
	}

	protected abstract boolean inHoverArea(Point p);

	protected abstract void startHover();

	protected abstract void endHover();
}
