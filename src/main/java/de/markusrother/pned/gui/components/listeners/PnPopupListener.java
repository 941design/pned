package de.markusrother.pned.gui.components.listeners;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import de.markusrother.pned.gui.components.menus.PnMenuFactory;
import de.markusrother.swing.PopupListener;

/**
 * <p>
 * PnGridPopupListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnPopupListener extends PopupListener {

	private final PnMenuFactory menuFactory;

	/**
	 * <p>
	 * Constructor for PnGridPopupListener.
	 * </p>
	 *
	 * @param menuFactory
	 *            a {@link de.markusrother.pned.gui.components.PnGridPanel}
	 *            object.
	 */
	public PnPopupListener(final PnMenuFactory menuFactory) {
		this.menuFactory = menuFactory;
	}

	/** {@inheritDoc} */
	@Override
	public void popup(final MouseEvent e) {
		final Point point = e.getPoint();
		final Component component = e.getComponent();
		final JPopupMenu popup = menuFactory.newPopupMenu(point);
		popup.show(component, point.x, point.y);
	}

}
