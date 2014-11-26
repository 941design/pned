package de.markusrother.pned.gui.menus.items;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.events.PlaceCreationRequest;

public class CreatePlaceMenuItem extends JMenuItem {

	private static final String label = "Create place";
	private static final CreatePlaceAction createPlaceAction = new CreatePlaceAction();

	static class CreatePlaceAction extends AbstractAction {

		CreatePlaceAction() {
			super(label);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.createPlace(new PlaceCreationRequest(this));
			// TODO - alternatively grid.createPlace();
		}
	}

	public CreatePlaceMenuItem() {
		super(createPlaceAction);
	}

}
