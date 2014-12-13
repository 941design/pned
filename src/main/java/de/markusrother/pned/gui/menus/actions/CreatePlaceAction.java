package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.swing.CustomRadioButtonMenuItem;

public class CreatePlaceAction extends AbstractNodeAction {

	private static final String label = "Create place";
	private static final int mnemonic = KeyEvent.VK_P;

	public static JRadioButtonMenuItem newMenuItem(final Object source, final LocationProvider locationProvider) {
		final CreatePlaceAction action = new CreatePlaceAction(source, locationProvider);
		final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
		menuItem.addItemListener(action);
		return menuItem;
	}

	public CreatePlaceAction(final Object source, final LocationProvider locationProvider) {
		super(source, locationProvider, mnemonic, label);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		eventBus.createPlace(new PlaceCreationRequest(source, locationProvider.getLocation()));
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setSelected(cmd.getMode() == PLACE);
	}

	@Override
	public void setSelected(final boolean enabled) {
		putValue(Action.NAME, label + (enabled ? " (default)" : ""));
	}

	@Override
	protected void selected() {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, PLACE));
	}

}
