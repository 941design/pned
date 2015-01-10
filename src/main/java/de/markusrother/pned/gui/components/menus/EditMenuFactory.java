package de.markusrother.pned.gui.components.menus;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import de.markusrother.pned.gui.actions.CreatePlaceAction;
import de.markusrother.pned.gui.actions.CreateTransitionAction;
import de.markusrother.pned.gui.actions.LocationProvider;
import de.markusrother.pned.gui.actions.RemoveIncomingEdgesAction;
import de.markusrother.pned.gui.actions.RemoveOutgoingEdgesAction;
import de.markusrother.pned.gui.actions.RemoveSelectedNodesAction;
import de.markusrother.pned.gui.control.PnState;

/**
 * <p>
 * EditMenuFactory class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EditMenuFactory
	implements
		MenuListener {

	/** Constant <code>label="Edit"</code> */
	private static final String label = "Edit";
	/** Constant <code>mnemonic=KeyEvent.VK_E</code> */
	private static final int mnemonic = KeyEvent.VK_E;

	private final PnState state;

	/**
	 * <p>
	 * Constructor for EditMenuFactory.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} object.
	 */
	public EditMenuFactory(final PnState state) {
		this.state = state;
	}

	/**
	 * <p>
	 * newMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newMenu() {
		final JMenu menu = new JMenu(label);
		populateEditMenu(menu, DefaultNodeLocationProvider.INSTANCE);
		menu.addMenuListener(this);
		menu.setMnemonic(mnemonic);
		return menu;
	}

	/**
	 * <p>
	 * newPopupMenu.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a {@link javax.swing.JPopupMenu} object.
	 */
	public JPopupMenu newPopupMenu(final Point point) {
		final JPopupMenu popup = new JPopupMenu(label);
		final LocationProvider locationProvider = new LocationProvider() {
			@Override
			public Point getLocation() {
				return point;
			}
		};
		populateEditMenu(popup, locationProvider);
		return popup;
	}

	/** {@inheritDoc} */
	@Override
	public void menuSelected(final MenuEvent e) {
		final JMenu menu = (JMenu) e.getSource();
		// TODO - This can be done more efficient!
		menu.removeAll();
		populateEditMenu(menu, DefaultNodeLocationProvider.INSTANCE);
	}

	/** {@inheritDoc} */
	@Override
	public void menuDeselected(final MenuEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void menuCanceled(final MenuEvent e) {
		// IGNORE
	}

	/**
	 * <p>
	 * populateEditMenu.
	 * </p>
	 *
	 * @param component
	 *            a {@link javax.swing.JComponent} object.
	 * @param locationProvider
	 *            a {@link de.markusrother.pned.gui.actions.LocationProvider}
	 *            object.
	 */
	private void populateEditMenu(final JComponent component, final LocationProvider locationProvider) {

		// TODO - Check type, cast, then add separators.

		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				state.getEventBus(), //
				locationProvider);
		buttonGroup.add(placeItem);
		component.add(placeItem);
		placeItem.setSelected(state.getNodeCreationMode() == PnState.NodeCreationMode.PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				state.getEventBus(), //
				locationProvider);
		buttonGroup.add(transitionItem);
		component.add(transitionItem);
		transitionItem.setSelected(state.getNodeCreationMode() == PnState.NodeCreationMode.TRANSITION);

		final JMenuItem removeNodesItem = RemoveSelectedNodesAction.newMenuItem(state);

		component.add(removeNodesItem);

		component.add(RemoveOutgoingEdgesAction.newMenuItem(state));
		component.add(RemoveIncomingEdgesAction.newMenuItem(state));
	}

}
