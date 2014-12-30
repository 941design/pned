package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.TransitionLayoutCommand;

/**
 * <p>TransitionLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionLayoutListener
	extends
		EventListener {

	/**
	 * <p>setSize.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.commands.TransitionLayoutCommand} object.
	 */
	void setSize(TransitionLayoutCommand cmd);
}
