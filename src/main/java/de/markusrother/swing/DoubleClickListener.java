package de.markusrother.swing;

import java.awt.event.MouseEvent;

/**
 * <p>
 * A mouse listener wrapper encapsulating double click events.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class DoubleClickListener extends MultiClickListener {

    /** {@inheritDoc} */
    @Override
    public void mouseClickedLeft(final MouseEvent e) {
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
