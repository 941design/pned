package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.actions.CreatePlaceAction;
import de.markusrother.pned.gui.actions.CreateTransitionAction;
import de.markusrother.pned.gui.actions.LocationProvider;
import de.markusrother.pned.gui.actions.RemoveSelectedNodesAction;
import de.markusrother.pned.gui.components.PnGridPanel;
import de.markusrother.pned.gui.components.PnGridPanel.State;
import de.markusrother.pned.gui.events.GuiEventBus;
import de.markusrother.swing.PopupListener;

/**
 * <p>
 * PnGridPopupListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnGridPopupListener extends PopupListener
	implements
		LocationProvider {

	/** Constant <code>NOT_ENABLED=false</code> */
	private static final boolean NOT_ENABLED = false;

	private final GuiEventBus eventBus;
	private final PnGridPanel pnGridPanel;

	private Point popupPoint;

	/**
	 * <p>
	 * Constructor for PnGridPopupListener.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param pnGridPanel
	 *            a {@link de.markusrother.pned.gui.components.PnGridPanel}
	 *            object.
	 */
	public PnGridPopupListener(final GuiEventBus eventBus, final PnGridPanel pnGridPanel) {
		this.eventBus = eventBus;
		this.pnGridPanel = pnGridPanel;
	}

	/** {@inheritDoc} */
	@Override
	public void popup(final MouseEvent e) {
		popupPoint = e.getPoint();
		// TODO - use the same menu as the edit menu!!!
		final JPopupMenu popup = new JPopupMenu();

		final Object eventSource = popup;
		final LocationProvider locationProvider = this;

		if (pnGridPanel.hasState(State.MULTISELECTION)) {
			popup.add(RemoveSelectedNodesAction.newMenuItem(eventBus, eventSource, NOT_ENABLED));
		}

		final NodeCreationMode mode = pnGridPanel.hasState(State.TRANSITION_CREATION) ? TRANSITION : PLACE;
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(placeItem);
		popup.add(placeItem);
		placeItem.setSelected(mode == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(transitionItem);
		popup.add(transitionItem);
		transitionItem.setSelected(mode == TRANSITION);

		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public Point getLocation() {
		return popupPoint;
	}

}
