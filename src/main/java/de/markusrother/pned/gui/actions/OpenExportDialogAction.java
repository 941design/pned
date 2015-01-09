package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.dialogs.FileDialogFactory;

/**
 * <p>
 * Action that opens an
 * {@link de.markusrother.pned.gui.components.dialogs.SaveFileDialog} upon
 * performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenExportDialogAction extends AbstractOpenFileDialogAction {

	/** Constant <code>menuLabel="Export"</code> */
	private static final String name = "Export";
	/** Constant <code>actionMnemonic=KeyEvent.VK_E</code> */
	private static final int actionMnemonic = KeyEvent.VK_E;

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection opens
	 * a {@link de.markusrother.pned.gui.components.dialogs.SaveFileDialog}.
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            a
	 *            {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            - to instantiate the dialog.
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
	 *            a
	 *            {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            - to instantiate the dialog.
	 */
	private OpenExportDialogAction(final FileDialogFactory fileDialogFactory) {
		super(fileDialogFactory, name, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		fileDialogFactory.openExportDialog();
	}

}
