package de.markusrother.pned.gui.actions;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.menus.DefaultNodeLocationProvider;

public class EditMenuFactory extends AbstractMenuFactory
	implements
		MenuListener {

	/** Constant <code>label="Edit"</code> */
	private static final String label = "Edit";
	/** Constant <code>mnemonic=KeyEvent.VK_E</code> */
	private static final int mnemonic = KeyEvent.VK_E;

	public EditMenuFactory(final GuiEventBus eventBus) {
		super(eventBus);
	}

	public JMenu newMenu() {
		final JMenu menu = new JMenu(label);
		populateEditMenu(menu, DefaultNodeLocationProvider.INSTANCE);
		menu.addMenuListener(this);
		menu.setMnemonic(mnemonic);
		return menu;
	}

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

	@Override
	public void menuSelected(final MenuEvent e) {
		final JMenu menu = (JMenu) e.getSource();
		// TODO - This can be done more efficient!
		menu.removeAll();
		populateEditMenu(menu, DefaultNodeLocationProvider.INSTANCE);
	}

	@Override
	public void menuDeselected(final MenuEvent e) {
		// IGNORE
	}

	@Override
	public void menuCanceled(final MenuEvent e) {
		// IGNORE
	}

	private void populateEditMenu(final JComponent component, final LocationProvider locationProvider) {
		final Object eventSource = component;
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(placeItem);
		component.add(placeItem);
		placeItem.setSelected(nodeCreationMode == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(transitionItem);
		component.add(transitionItem);
		transitionItem.setSelected(nodeCreationMode == TRANSITION);

		component.add(RemoveSelectedNodesAction.newMenuItem(eventBus, //
				eventSource, //
				areNodesSelected));

		// final HashMap<String, String> edgeToNodeId = new
		// HashMap<>(incomingEdgesMap);
		// component.add(RemoveIncomingEdgesAction.newMenuItem(eventBus, //
		// eventSource, //
		// edgeToNodeId));
		// component.add(RemoveOutgoingEdgesAction.newMenuItem(eventBus,
		// eventSource, selectedNodes));
	}

}
