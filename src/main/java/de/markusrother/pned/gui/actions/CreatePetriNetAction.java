package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.PetriNetEditCommand.Type;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;

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
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection
	 * triggers creation of a new Petri net.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.listeners.GuiCommandTarget}
	 *            to be posted to.
	 * @return a {@link javax.swing.JMenuItem} with this action bound.
	 */
	public static JMenuItem newMenuItem(final GuiCommandTarget eventTarget) {
		return new JMenuItem(new CreatePetriNetAction(eventTarget));
	}

	private final GuiCommandTarget eventTarget;

	/**
	 * <p>
	 * Constructor for NewNetAction.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.listeners.GuiCommandTarget}
	 *            to be posted to.
	 */
	private CreatePetriNetAction(final GuiCommandTarget eventTarget) {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
		this.eventTarget = eventTarget;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this, Type.NEW);
		eventTarget.createPetriNet(cmd);
	}

}
