package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.dialogs.FileDialogFactory;

/**
 * <p>
 * * Action that opens an
 * {@link de.markusrother.pned.gui.components.dialogs.OpenFileDialog} upon
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenImportDialogAction extends AbstractOpenFileDialogAction {

	/** Constant <code>menuLabel="Import"</code> */
	private static final String name = "Import";
	/** Constant <code>actionMnemonic=KeyEvent.VK_I</code> */
	private static final int actionMnemonic = KeyEvent.VK_I;

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection opens
	 * an {@link de.markusrother.pned.gui.components.dialogs.OpenFileDialog}.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 * @param fileDialogFactory
	 *            a
	 *            {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            - to instantiate the dialog.
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
	 *            an
	 *            {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            - to instantiate the dialog.
	 */
	private OpenImportDialogAction(final FileDialogFactory fileDialogFactory) {
		super(fileDialogFactory, name, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		fileDialogFactory.openImportDialog();
	}

}
