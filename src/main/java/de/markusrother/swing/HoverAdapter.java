package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

/**
 * <p>
 * HoverAdapter class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class HoverAdapter extends HoverListener {

    /** {@inheritDoc} */
    @Override
    protected boolean inHoverArea(final Component component, final Point p) {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected void startHover(final Component component) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    protected void endHover(final Component component) {
        // IGNORE
    }

}
