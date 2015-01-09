package de.markusrother.pned.control.commands;

import java.util.EventListener;

/**
 * <p>
 * TransitionListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface TransitionListener
	extends
		EventListener {

	/**
	 * <p>
	 * fireTransition.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.control.commands.TransitionExecutionCommand}
	 *            object.
	 */
	void fireTransition(TransitionExecutionCommand cmd);

}
