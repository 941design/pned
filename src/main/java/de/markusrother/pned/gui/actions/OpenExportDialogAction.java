package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.dialogs.SaveFileDialog;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

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
public class OpenExportDialogAction extends AbstractStatefulAction {

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
	 * @param state a {@link de.markusrother.pned.gui.core.model.PnStateModel} object.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final PnStateModel state, final PnCommandTarget commandTarget) {
		final Action action = new OpenExportDialogAction(state, commandTarget);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for OpenExportDialogAction.
	 * </p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.core.model.PnStateModel} object.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	private OpenExportDialogAction(final PnStateModel state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, name, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		SaveFileDialog.open(state, commandTarget);
	}

}
