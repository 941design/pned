package de.markusrother.swing;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

import sun.swing.MenuItemLayoutHelper;

/**
 * <p>
 * Helper to distinguish a menu item click into a) toggle of a radio button or
 * b) actual selection.
 * </p>
 * 
 * TODO - This is a hacky solution which is but should not be UI dependent!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CustomRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI
    implements
        MouseListener {

    private MenuSelectionManager cachedMsm;

    /**
     * <p>
     * Constructor for CustomRadioButtonMenuItemUI.
     * </p>
     *
     * @param menuItem
     *            a {@link de.markusrother.swing.CustomRadioButtonMenuItem}
     *            object.
     */
    CustomRadioButtonMenuItemUI(final CustomRadioButtonMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    /**
     * <p>
     * waitForCachingMsm.
     * </p>
     */
    private void waitForCachingMsm() {
        while (cachedMsm == null) {
            try {
                wait();
            } catch (final InterruptedException e1) {
                // IGNORE
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    protected synchronized void doClick(final MenuSelectionManager msm) {
        // We will proceed upon determining whether or not the MouseEvent
        // was on the radio button. Caching is necessary if we do not rely
        // on the order of registered MouseListeners.
        this.cachedMsm = msm;
        notify();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void mouseClicked(final MouseEvent e) {
        final Rectangle textRect = getTextRect();
        // We might as well listen for the (small) checkRect
        if (textRect.contains(e.getPoint())) {
            waitForCachingMsm();
            super.doClick(cachedMsm);
        } else {
            // TODO - We could actually register callers via a custom listener
            // for either event.
            // Only radio button was toggled!
            menuItem.setSelected(true);
        }
        cachedMsm = null;
    }

    /**
     * <p>
     * getTextRect.
     * </p>
     *
     * @return a {@link java.awt.Rectangle} object.
     */
    private Rectangle getTextRect() {
        // This code was copied from
        // javax.swing.plaf.basic.BasicMenuItemUI.paintMenuItem()
        // Not sure if there is a cleaner way how to retrieve the actual
        // rect.
        final Rectangle viewRect = new Rectangle(0, 0, menuItem.getWidth(), menuItem.getHeight());
        final MenuItemLayoutHelper lh = new MenuItemLayoutHelper(menuItem, checkIcon, arrowIcon, viewRect,
                defaultTextIconGap, acceleratorDelimiter, true, menuItem.getFont(), acceleratorFont,
                MenuItemLayoutHelper.useCheckAndArrow(menuItem), getPropertyPrefix());
        final MenuItemLayoutHelper.LayoutResult lr = lh.layoutMenuItem();
        // lr.getCheckRect();
        return lr.getTextRect();
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(final MouseEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased(final MouseEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void mouseEntered(final MouseEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void mouseExited(final MouseEvent e) {
        // IGNORE
    }

}
