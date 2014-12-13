package de.markusrother.pned.gui.menus.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

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

	private static final String menuLabel = "Import";
	private static final int actionMnemonic = KeyEvent.VK_I;
	private static final String dialogTitle = "Import file";
	private static final String approveButtonLabel = "Import";
	private static final Component NO_PARENT = null;

	public static JMenuItem newMenuItem() {
		return new JMenuItem(new OpenImportDialogAction());
	}

	private OpenImportDialogAction() {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
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
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(dialogTitle);
		chooser.setMultiSelectionEnabled(false);
		// setCurrentDirectory()
		// addChoosableFileFilter()
		// setFileFilter()
		final int result = chooser.showDialog(NO_PARENT, approveButtonLabel);
		if (result == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getSelectedFile());
			// TODO
			throw new RuntimeException("TODO");
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// IGNORE
		} else {
			throw new IllegalStateException();
		}
	}

}
