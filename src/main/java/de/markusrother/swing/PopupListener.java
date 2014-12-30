package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>Abstract PopupListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PopupListener extends MouseAdapter {

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(final MouseEvent e) {
		maybePopup(e);
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(final MouseEvent e) {
		maybePopup(e);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(final MouseEvent e) {
		maybePopup(e);
	}

	/**
	 * <p>maybePopup.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	private void maybePopup(final MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup(e);
		}
	}

	/**
	 * <p>popup.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	public abstract void popup(MouseEvent e);

}
