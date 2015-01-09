package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.control.commands.PetriNetEditCommand.Type;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Action that triggers creation of a new Petri net upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class CreatePetriNetAction extends AbstractStatelessAction {

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
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final PnCommandTarget commandTarget) {
		return new JMenuItem(new CreatePetriNetAction(commandTarget));
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
	 */
	private CreatePetriNetAction(final PnCommandTarget eventTarget) {
		super(eventTarget, name);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this, Type.NEW);
		commandTarget.createPetriNet(cmd);
	}

}
