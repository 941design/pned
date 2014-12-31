package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.EventTarget;

/**
 * <p>
 * Action that triggers creation of a new Petri net upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CreatePetriNetAction extends AbstractAction {

	/** Constant <code>menuLabel="New"</code> */
	private static final String menuLabel = "New";
	/** Constant <code>actionMnemonic=KeyEvent.VK_N</code> */
	private static final int actionMnemonic = KeyEvent.VK_N;

	/**
	 * <p>
	 * Creates and returns a {@link JMenuItem} where selection triggers creation
	 * of a new Petri net.
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to be posted
	 *            to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final EventTarget eventTarget) {
		return new JMenuItem(new CreatePetriNetAction(eventTarget));
	}

	private final EventTarget eventTarget;

	/**
	 * <p>
	 * Constructor for NewNetAction.
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to be posted
	 *            to.
	 */
	private CreatePetriNetAction(final EventTarget eventTarget) {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
		this.eventTarget = eventTarget;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this);
		eventTarget.createPetriNet(cmd);
	}

}
