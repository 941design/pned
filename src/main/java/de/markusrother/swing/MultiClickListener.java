package de.markusrother.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

/**
 * <p>Abstract MultiClickListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class MultiClickListener extends MouseAdapter {

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(final MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (e.getClickCount() >= 2) {
				mouseDoubleClickedLeft(e);
			} else {
				mouseClickedLeft(e);
			}
		} else if (SwingUtilities.isRightMouseButton(e)) {
			mouseClickedRight(e);
		} else if (SwingUtilities.isMiddleMouseButton(e)) {
			mouseClickedMiddle(e);
		}
	}

	/**
	 * <p>mouseClickedLeft.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	public abstract void mouseClickedLeft(final MouseEvent e);

	/**
	 * <p>mouseDoubleClickedLeft.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	public abstract void mouseDoubleClickedLeft(final MouseEvent e);

	/**
	 * <p>mouseClickedRight.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	public abstract void mouseClickedRight(final MouseEvent e);

	/**
	 * <p>mouseClickedMiddle.</p>
	 *
	 * @param e a {@link java.awt.event.MouseEvent} object.
	 */
	public abstract void mouseClickedMiddle(final MouseEvent e);

}
