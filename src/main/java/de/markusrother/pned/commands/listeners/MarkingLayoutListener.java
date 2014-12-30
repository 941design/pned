package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.MarkingLayoutCommand;

/**
 * <p>MarkingLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface MarkingLayoutListener
	extends
		EventListener {

	/**
	 * <p>setSize.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.commands.MarkingLayoutCommand} object.
	 */
	void setSize(MarkingLayoutCommand cmd);

}
