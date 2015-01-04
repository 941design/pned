package de.markusrother.pned.gui.listeners;

import java.awt.event.MouseEvent;

import de.markusrother.pned.core.commands.TransitionExecutionCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.components.Transition;
import de.markusrother.swing.RightClickListener;

public class TransitionActivator extends RightClickListener {

	private final EventBus eventBus;

	public TransitionActivator(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void mouseClickedRight(final MouseEvent e) {
		final Transition transition = (Transition) e.getSource();
		TransitionExecutionCommand cmd = new TransitionExecutionCommand(this, transition.getId());
		eventBus.fireTransition(cmd);
	}

}
