package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.dialogs.FileDialogFactory;

/**
 * <p>
 * Action that opens an ExportDialog (FIXME) upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenExportDialogAction extends AbstractOpenFileDialogAction {

	/** Constant <code>menuLabel="Export"</code> */
	private static final String menuLabel = "Export";
	/** Constant <code>actionMnemonic=KeyEvent.VK_E</code> */
	private static final int actionMnemonic = KeyEvent.VK_E;

	/**
	 * <p>
	 * Creates and returns a {@link JMenuItem} where selection opens a
	 * ExportDialog. (FIXME)
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            a {@link de.markusrother.pned.gui.dialogs.FileDialogFactory}.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final FileDialogFactory fileDialogFactory) {
		final Action action = new OpenExportDialogAction(fileDialogFactory);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for OpenExportDialogAction.
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            a {@link de.markusrother.pned.gui.dialogs.FileDialogFactory}.
	 */
	private OpenExportDialogAction(final FileDialogFactory fileDialogFactory) {
		super(fileDialogFactory, menuLabel, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		fileDialogFactory.openExportDialog();
	}

}
