package de.markusrother.swing;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

import sun.swing.MenuItemLayoutHelper;

/**
 * <p>CustomRadioButtonMenuItem class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CustomRadioButtonMenuItem extends JRadioButtonMenuItem {

	class CustomRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI
		implements
			MouseListener {

		private MenuSelectionManager cachedMsm;

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
				// Only radio button was toggled!
				CustomRadioButtonMenuItem.this.setSelected(true);
			}
			cachedMsm = null;
		}

		private Rectangle getTextRect() {
			// This code was copied from
			// javax.swing.plaf.basic.BasicMenuItemUI.paintMenuItem()
			// Not sure if there is a cleaner way how to retrieve the actual
			// rect.
			final CustomRadioButtonMenuItem mi = CustomRadioButtonMenuItem.this;
			final Rectangle viewRect = new Rectangle(0, 0, mi.getWidth(), mi.getHeight());
			final MenuItemLayoutHelper lh = new MenuItemLayoutHelper(mi, checkIcon, arrowIcon, viewRect,
					defaultTextIconGap, acceleratorDelimiter, true, mi.getFont(), acceleratorFont,
					MenuItemLayoutHelper.useCheckAndArrow(menuItem), getPropertyPrefix());
			final MenuItemLayoutHelper.LayoutResult lr = lh.layoutMenuItem();
			// lr.getCheckRect();
			return lr.getTextRect();
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

	/**
	 * <p>Constructor for CustomRadioButtonMenuItem.</p>
	 *
	 * @param action a {@link javax.swing.AbstractAction} object.
	 */
	public CustomRadioButtonMenuItem(final AbstractAction action) {
		super(action);
		setUI(new CustomRadioButtonMenuItemUI());
		addMouseListener((MouseListener) ui);
	}

}
