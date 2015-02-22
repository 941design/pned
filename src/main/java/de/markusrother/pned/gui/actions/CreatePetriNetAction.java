package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.markusrother.pned.gui.control.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.control.commands.PetriNetEditCommand.Type;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

/**
 * <p>
 * Action that triggers creation of a new Petri net upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class CreatePetriNetAction extends AbstractStatefulAction {

	/** Constant <code>menuLabel="New"</code> */
	private static final String name = "New";
	/** Constant <code>actionMnemonic=KeyEvent.VK_N</code> */
	private static final int actionMnemonic = KeyEvent.VK_N;

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection
	 * triggers creation of a new Petri net.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.model.PnStateModel}
	 *            object.
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final PnStateModel state, final PnCommandTarget commandTarget) {
		return new JMenuItem(new CreatePetriNetAction(state, commandTarget));
	}

	/**
	 * <p>
	 * Constructor for NewNetAction.
	 * </p>
	 *
	 * @param eventTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.model.PnStateModel}
	 *            object.
	 */
	private CreatePetriNetAction(final PnStateModel state, final PnCommandTarget eventTarget) {
		super(state, eventTarget, name);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (state.isDirty()) {
			// TODO - Could also be wrapped in an action!
			final int result = JOptionPane.showConfirmDialog(null,
					"The current net contains unsaved changes, which will be discarded. Proceed?", "Confirm creation",
					JOptionPane.YES_NO_OPTION);
			if (result != 0) {
				return;
			}
		}
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this, Type.NEW);
		commandTarget.createPetriNet(cmd);
	}

}
