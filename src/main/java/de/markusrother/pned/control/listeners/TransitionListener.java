package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.TransitionExecutionCommand;

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
	 * @param cmd a {@link de.markusrother.pned.control.commands.TransitionExecutionCommand} object.
	 */
	void fireTransition(TransitionExecutionCommand cmd);

}
