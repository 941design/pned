package de.markusrother.pned.gui.commands;

import java.util.EventListener;

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
	 * @param cmd a {@link de.markusrother.pned.gui.commands.TransitionLayoutCommand} object.
	 */
	void setSize(TransitionLayoutCommand cmd);
}
