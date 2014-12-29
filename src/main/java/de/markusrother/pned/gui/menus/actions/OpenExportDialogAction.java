package de.markusrother.pned.gui.menus.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.EventBus;

public class OpenExportDialogAction extends AbstractAction {

	private static final String menuLabel = "Export";
	private static final int actionMnemonic = KeyEvent.VK_E;
	private static final String dialogTitle = "Export file";
	private static final String approveButtonLabel = "Save";
	private static final Component NO_PARENT = null;

	public static JMenuItem newMenuItem(final EventBus eventMulticaster) {
		return new JMenuItem(new OpenExportDialogAction(eventMulticaster));
	}

	private final EventBus eventBus;

	private OpenExportDialogAction(final EventBus eventMulticaster) {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);

		this.eventBus = eventMulticaster;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO - open with current path:
		final JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(dialogTitle);
		chooser.setMultiSelectionEnabled(false);
		// setCurrentDirectory()
		// addChoosableFileFilter()
		// setFileFilter()
		final int result = chooser.showDialog(NO_PARENT, approveButtonLabel);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			// TODO - prompt for overwrite!
			try {
				eventBus.exportPnml(new PetriNetIOCommand(this, selectedFile));
			} catch (final IOException e1) {
				// TODO
				throw new RuntimeException("TODO");
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// IGNORE
		} else {
			throw new IllegalStateException();
		}
	}

}
