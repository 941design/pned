package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.TransitionExecutionCommand;

/**
 * <p>TransitionListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionListener
	extends
		EventListener {

	/**
	 * <p>fireTransition.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.core.commands.TransitionExecutionCommand} object.
	 */
	void fireTransition(TransitionExecutionCommand cmd);

}
