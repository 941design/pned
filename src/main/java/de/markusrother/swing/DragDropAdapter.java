package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

/**
 * <p>
 * DragDropAdapter class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class DragDropAdapter<T extends Component> extends DragDropListener<T> {

    /**
     * <p>
     * Constructor for DragDropAdapter.
     * </p>
     *
     * @param clazz a {@link java.lang.Class} object.
     */
    protected DragDropAdapter(final Class<T> clazz) {
        super(clazz);
    }

    /** {@inheritDoc} */
    @Override
    protected void startDrag(final T component, final Point point) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    protected void endDrag(final T component, final Point point) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    protected void onDrag(final T component, final int deltaX, final int deltaY) {
        // IGNORE
    }

}
