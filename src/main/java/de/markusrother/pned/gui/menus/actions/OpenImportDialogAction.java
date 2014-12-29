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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.EventBus;

/**
 * 
 * TODO
 * 
 * By using the same file chooser instance to display its open and save dialogs,
 * the program reaps the following benefits:
 * 
 * The chooser remembers the current directory between uses, so the open and
 * save versions automatically share the same current directory. You have to
 * customize only one file chooser, and the customizations apply to both the
 * open and save versions.
 *
 */
public class OpenImportDialogAction extends AbstractAction {

	public static FileFilter filter = new FileNameExtensionFilter(".pnml", "pnml");

	private static final String menuLabel = "Import";
	private static final int actionMnemonic = KeyEvent.VK_I;
	private static final String dialogTitle = "Import file";
	private static final String approveButtonLabel = "Import";
	private static final Component NO_PARENT = null;

	public static JMenuItem newMenuItem(final EventBus eventMulticaster) {
		return new JMenuItem(new OpenImportDialogAction(eventMulticaster));
	}

	private final EventBus eventBus;

	private OpenImportDialogAction(final EventBus eventMulticaster) {
		super(menuLabel);
		// this.source = source; // TODO
		putValue(Action.MNEMONIC_KEY, actionMnemonic);

		this.eventBus = eventMulticaster;
	}

	/**
	 * NOTE - The <code>parent</code> argument determines two things: the frame
	 * on which the open dialog depends and the component whose position the
	 * look and feel should consider when placing the dialog. If the parent is a
	 * <code>Frame</code> object (such as a <code>JFrame</code>) then the dialog
	 * depends on the frame and the look and feel positions the dialog relative
	 * to the frame (for example, centered over the frame). If the parent is a
	 * component, then the dialog depends on the frame containing the component,
	 * and is positioned relative to the component (for example, centered over
	 * the component). If the parent is <code>null</code>, then the dialog
	 * depends on no visible window, and it's placed in a
	 * look-and-feel-dependent position such as the center of the screen.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO - open with current path:
		final JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(dialogTitle);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		// setCurrentDirectory()

		final int result = chooser.showDialog(NO_PARENT, approveButtonLabel);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			// TODO - confirm if unsaved.
			// TODO - maintain dirty flag!
			// If current net is to be purged send appropriate command
			try {
				eventBus.importPnml(new PetriNetIOCommand(null, selectedFile));
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
