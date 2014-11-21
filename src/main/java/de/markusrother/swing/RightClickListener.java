package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public abstract class RightClickListener extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			mouseClickedRight(e);
		}
	}

	public abstract void mouseClickedRight(final MouseEvent e);

}
