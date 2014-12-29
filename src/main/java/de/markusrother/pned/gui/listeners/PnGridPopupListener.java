package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.components.PnGridPanel;
import de.markusrother.pned.gui.components.PnGridPanel.State;
import de.markusrother.pned.gui.menus.actions.CreatePlaceAction;
import de.markusrother.pned.gui.menus.actions.CreateTransitionAction;
import de.markusrother.pned.gui.menus.actions.LocationProvider;
import de.markusrother.pned.gui.menus.actions.RemoveSelectedNodesAction;
import de.markusrother.swing.PopupListener;

public class PnGridPopupListener extends PopupListener
	implements
		LocationProvider {

	private static final boolean NOT_ENABLED = false;

	private final EventBus eventMulticaster;
	private final PnGridPanel pnGridPanel;

	private Point popupPoint;

	public PnGridPopupListener(final EventBus eventMulticaster, final PnGridPanel pnGridPanel) {
		this.eventMulticaster = eventMulticaster;
		this.pnGridPanel = pnGridPanel;
	}

	@Override
	public void popup(final MouseEvent e) {
		popupPoint = e.getPoint();
		// TODO - use the same menu as the edit menu!!!
		final JPopupMenu popup = new JPopupMenu();

		final Object eventSource = popup;
		final LocationProvider locationProvider = this;

		if (pnGridPanel.hasState(State.MULTISELECTION)) {
			popup.add(RemoveSelectedNodesAction.newMenuItem(eventMulticaster, eventSource, NOT_ENABLED));
		}

		final NodeCreationMode mode = pnGridPanel.hasState(State.TRANSITION_CREATION) ? TRANSITION : PLACE;
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				eventMulticaster, //
				eventSource, //
				locationProvider);
		buttonGroup.add(placeItem);
		popup.add(placeItem);
		placeItem.setSelected(mode == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				eventMulticaster, //
				eventSource, //
				locationProvider);
		buttonGroup.add(transitionItem);
		popup.add(transitionItem);
		transitionItem.setSelected(mode == TRANSITION);

		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	@Override
	public Point getLocation() {
		return popupPoint;
	}

}
