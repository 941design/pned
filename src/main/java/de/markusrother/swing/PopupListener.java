package de.markusrother.swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <p>
 * A mouse listener wrapper encapsulating popup request events.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PopupListener extends MouseAdapter {

    /**
     * <p>
     * addToComponent.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     * @param listener
     *            a {@link de.markusrother.swing.MultiClickListener} object.
     */
    public static void addToComponent(final Component component, final PopupListener listener) {
        component.addMouseListener(listener);
    }

    /**
     * <p>
     * removeFromComponent.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     * @param listener
     *            a {@link de.markusrother.swing.MultiClickListener} object.
     */
    public static void removeFromComponent(final Component component, final PopupListener listener) {
        component.removeMouseListener(listener);
    }

    /**
     * <p>
     * addToComponent.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     */
    public void addToComponent(final Component component) {
        addToComponent(component, this);
    }

    /**
     * <p>
     * removeFromComponent.
     * </p>
     *
     * @param component
     *            a {@link java.awt.Component} object.
     */
    public void removeFromComponent(final Component component) {
        removeFromComponent(component, this);
    }

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
     * <p>
     * maybePopup.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    private void maybePopup(final MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup(e);
        }
    }

    /**
     * <p>
     * popup.
     * </p>
     *
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    public abstract void popup(MouseEvent e);

}
