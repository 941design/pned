package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class DoubleClickListener extends MouseAdapter {

	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			mouseDoubleClicked(e);
		}
	}

	public abstract void mouseDoubleClicked(final MouseEvent e);
}
