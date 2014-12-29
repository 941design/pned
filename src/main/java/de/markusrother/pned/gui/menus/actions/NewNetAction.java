package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.EventBus;

public class NewNetAction extends AbstractAction {

	private static final String menuLabel = "New";
	private static final int actionMnemonic = KeyEvent.VK_N;

	public static JMenuItem newMenuItem(final EventBus eventMulticaster) {
		return new JMenuItem(new NewNetAction(eventMulticaster));
	}

	private final EventBus eventBus;

	private NewNetAction(final EventBus eventMulticaster) {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
		this.eventBus = eventMulticaster;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this);
		eventBus.disposePetriNet(cmd);
	}

}
