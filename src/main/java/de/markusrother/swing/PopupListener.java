package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class PopupListener extends MouseAdapter {

	@Override
	public void mouseReleased(final MouseEvent e) {
		maybePopup(e);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		maybePopup(e);
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		maybePopup(e);
	}

	private void maybePopup(final MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup(e);
		}
	}

	public abstract void popup(MouseEvent e);

}
