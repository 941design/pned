package de.markusrother.pned.gui.actions;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

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

import de.markusrother.pned.gui.core.GuiState;
import de.markusrother.pned.gui.menus.DefaultNodeLocationProvider;

/**
 * <p>EditMenuFactory class.</p>
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

	private final GuiState state;

	/**
	 * <p>Constructor for EditMenuFactory.</p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.core.GuiState} object.
	 */
	public EditMenuFactory(final GuiState state) {
		this.state = state;
	}

	/**
	 * <p>newMenu.</p>
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
	 * <p>newPopupMenu.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
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
	 * <p>populateEditMenu.</p>
	 *
	 * @param component a {@link javax.swing.JComponent} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.actions.LocationProvider} object.
	 */
	private void populateEditMenu(final JComponent component, final LocationProvider locationProvider) {
		final Object eventSource = component;
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				state.getEventBus(), //
				eventSource, //
				locationProvider);
		buttonGroup.add(placeItem);
		component.add(placeItem);
		placeItem.setSelected(state.getNodeCreationMode() == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				state.getEventBus(), //
				eventSource, //
				locationProvider);
		buttonGroup.add(transitionItem);
		component.add(transitionItem);
		transitionItem.setSelected(state.getNodeCreationMode() == TRANSITION);

		final JMenuItem removeNodesItem = RemoveSelectedNodesAction.newMenuItem(eventSource, state);

		component.add(removeNodesItem);

		component.add(RemoveOutgoingEdgesAction.newMenuItem(eventSource, state));
		component.add(RemoveIncomingEdgesAction.newMenuItem(eventSource, state));
	}

}
