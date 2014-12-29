package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.components.settings.EditSettingsDialog;

public class EditSettingsAction extends AbstractAction {

	private static final String label = "Settings";
	private static final int mnemonic = KeyEvent.VK_S;

	public static JMenuItem newMenuItem(final EventBus eventMulticaster, final Object eventSource) {
		final EditSettingsAction action = new EditSettingsAction(eventMulticaster, eventSource);
		return new JMenuItem(action);
	}

	private final Object source;
	private final EventBus eventMulticaster;

	public EditSettingsAction(final EventBus eventMulticaster, final Object source) {
		super(label);
		this.eventMulticaster = eventMulticaster;
		this.source = source;
		putValue(Action.MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		EditSettingsDialog.open(eventMulticaster);
	}

}
