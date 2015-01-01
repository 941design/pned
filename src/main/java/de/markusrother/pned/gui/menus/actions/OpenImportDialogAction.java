package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.dialogs.FileDialogFactory;

/**
 * <p>
 * Action that opens an ImportDialog (FIXME) upon performing.
 * </p>
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
 * @author Markus Rother
 * @version 1.0
 */
public class OpenImportDialogAction extends AbstractOpenFileDialogAction {

	/** Constant <code>menuLabel="Import"</code> */
	private static final String menuLabel = "Import";
	/** Constant <code>actionMnemonic=KeyEvent.VK_I</code> */
	private static final int actionMnemonic = KeyEvent.VK_I;

	/**
	 * <p>
	 * Creates and returns a {@link JMenuItem} where selection opens a
	 * ImportDialog. (FIXME)
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final FileDialogFactory fileDialogFactory) {
		final Action action = new OpenImportDialogAction(fileDialogFactory);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for OpenImportDialogAction.
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 */
	private OpenImportDialogAction(final FileDialogFactory fileDialogFactory) {
		super(fileDialogFactory, menuLabel, actionMnemonic);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		fileDialogFactory.openImportDialog();
	}

}
