package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.dialogs.OpenFileDialog;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

/**
 * <p>
 * * Action that opens an
 * {@link de.markusrother.pned.gui.components.dialogs.OpenFileDialog} upon
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenImportDialogAction extends AbstractStatefulAction {

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
	 * @param state a {@link de.markusrother.pned.gui.core.model.PnStateModel} object.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	public static JMenuItem newMenuItem(final PnStateModel state, final PnCommandTarget commandTarget) {
		final Action action = new OpenImportDialogAction(state, commandTarget);
		return new JMenuItem(action);
	}

	/**
	 * <p>
	 * Constructor for OpenImportDialogAction.
	 * </p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.core.model.PnStateModel} object.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	private OpenImportDialogAction(final PnStateModel state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, name, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		OpenFileDialog.open(state, commandTarget);
	}

}
