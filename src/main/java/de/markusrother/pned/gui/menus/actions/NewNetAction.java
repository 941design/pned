package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.EventBus;

/**
 * <p>NewNetAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NewNetAction extends AbstractAction {

	/** Constant <code>menuLabel="New"</code> */
	private static final String menuLabel = "New";
	/** Constant <code>actionMnemonic=KeyEvent.VK_N</code> */
	private static final int actionMnemonic = KeyEvent.VK_N;

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final EventBus eventMulticaster) {
		return new JMenuItem(new NewNetAction(eventMulticaster));
	}

	private final EventBus eventBus;

	/**
	 * <p>Constructor for NewNetAction.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	private NewNetAction(final EventBus eventMulticaster) {
		super(menuLabel);
		putValue(Action.MNEMONIC_KEY, actionMnemonic);
		this.eventBus = eventMulticaster;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final PetriNetEditCommand cmd = new PetriNetEditCommand(this);
		eventBus.disposePetriNet(cmd);
	}

}
