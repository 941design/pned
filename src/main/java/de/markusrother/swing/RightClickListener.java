package de.markusrother.swing;

import java.awt.event.MouseEvent;

/**
 * <p>Abstract RightClickListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class RightClickListener extends MultiClickListener {

	/** {@inheritDoc} */
	@Override
	public void mouseClickedLeft(final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDoubleClickedLeft(final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedMiddle(final MouseEvent e) {
		// IGNORE
	}

}
