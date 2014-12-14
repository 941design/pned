package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.settings.EditSettingsDialog;

public class EditSettingsAction extends AbstractAction {

	private static final String label = "Settings";
	private static final int mnemonic = KeyEvent.VK_S;

	public static JMenuItem newMenuItem(final Object eventSource) {
		return new JMenuItem(new EditSettingsAction(eventSource));
	}

	private final Object source;

	public EditSettingsAction(final Object source) {
		super(label);
		this.source = source;
		putValue(Action.MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		EditSettingsDialog.open();
	}

}
