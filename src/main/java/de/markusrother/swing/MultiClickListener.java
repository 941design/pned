package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public abstract class MultiClickListener extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			mouseClickedLeft(e);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			mouseClickedRight(e);
		} else if (SwingUtilities.isMiddleMouseButton(e)) {
			mouseClickedMiddle(e);
		}
	}

	public abstract void mouseClickedLeft(final MouseEvent e);

	public abstract void mouseClickedRight(final MouseEvent e);

	public abstract void mouseClickedMiddle(final MouseEvent e);

}
