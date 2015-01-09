package de.markusrother.swing;

import java.awt.event.MouseEvent;

/**
 * <p>Abstract LeftClickListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class LeftClickListener extends MultiClickListener {

	/** {@inheritDoc} */
	@Override
	public void mouseDoubleClickedLeft(final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedRight(final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedMiddle(final MouseEvent e) {
		// IGNORE
	}

}
