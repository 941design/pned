package de.markusrother.pned.gui.menus.items;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.events.TransitionCreationRequest;

public class CreateTransitionMenuItem extends JMenuItem {

	private static final String label = "Create transition";
	private static final CreateTransitionAction createTransitionAction = new CreateTransitionAction();

	static class CreateTransitionAction extends AbstractAction {

		CreateTransitionAction() {
			super(label);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.createTransition(new TransitionCreationRequest(this));
			// TODO - alternatively grid.createTransition();
		}
	}

	public CreateTransitionMenuItem() {
		super(createTransitionAction);
	}
}
