package de.markusrother.swing;

import java.awt.Component;
import java.awt.Rectangle;

/**
 * <p>
 * DefaultDragDropListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class DefaultDragDropListener<T extends Component> extends DragDropAdapter<T> {

    private final T responsiveComponent;

    /**
     * <p>
     * Constructor for DefaultDragDropListener.
     * </p>
     *
     * @param type
     *            a {@link java.lang.Class} object.
     * @param responsiveComponent
     *            a T object.
     */
    public DefaultDragDropListener(final Class<T> type, final T responsiveComponent) {
        super(type);
        this.responsiveComponent = responsiveComponent;
    }

    /** {@inheritDoc} */
    @Override
    protected void onDrag(final T component, final int deltaX, final int deltaY) {
        final Rectangle r = responsiveComponent.getBounds();
        r.translate(deltaX, deltaY);
        responsiveComponent.setBounds(r);
    }

}
