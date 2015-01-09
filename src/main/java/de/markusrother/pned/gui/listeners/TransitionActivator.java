package de.markusrother.pned.gui.listeners;

import java.awt.event.MouseEvent;

import de.markusrother.pned.core.commands.TransitionExecutionCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.components.Transition;
import de.markusrother.swing.RightClickListener;

/**
 * <p>
 * TransitionActivator class.
 * </p>
 *
 * TODO - instantiate RCL with Action
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionActivator extends RightClickListener {

	private final EventBus eventBus;

	/**
	 * <p>
	 * Constructor for TransitionActivator.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public TransitionActivator(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedRight(final MouseEvent e) {
		final Transition transition = (Transition) e.getSource();
		final TransitionExecutionCommand cmd = new TransitionExecutionCommand(this, transition.getId());
		eventBus.fireTransition(cmd);
	}

}
