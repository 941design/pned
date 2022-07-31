package de.markusrother.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Helper to distinguish a menu item click into a) toggle of a radio button or
 * b) actual selection.
 */
public class CustomRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI implements MouseListener {

    private MenuSelectionManager cachedMsm;

    CustomRadioButtonMenuItemUI(final CustomRadioButtonMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    private void waitForCachingMsm() {
        while (cachedMsm == null) {
            try {
                wait();
            } catch (final InterruptedException e1) {
                // IGNORE
            }
        }
    }

    @Override
    protected synchronized void doClick(final MenuSelectionManager msm) {
        // We will proceed upon determining whether or not the MouseEvent
        // was on the radio button. Caching is necessary if we do not rely
        // on the order of registered MouseListeners.
        this.cachedMsm = msm;
        notify();
    }

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

    private Rectangle getTextRect() {
        // 1.0-SNAPSHOT implementation no longer works with upated dependencies.
        // Not sure what the intention of this method was in the first place.
        final Rectangle viewRect = new Rectangle(0, 0, menuItem.getWidth(), menuItem.getHeight());
        return viewRect;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        // IGNORE
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        // IGNORE
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        // IGNORE
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        // IGNORE
    }

}
