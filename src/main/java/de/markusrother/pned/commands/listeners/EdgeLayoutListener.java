package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.EdgeLayoutCommand;

/**
 * <p>EdgeLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeLayoutListener
	extends
		EventListener {

	/**
	 * <p>setSize.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.commands.EdgeLayoutCommand} object.
	 */
	void setSize(EdgeLayoutCommand cmd);

}
