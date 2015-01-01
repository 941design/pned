package de.markusrother.pned.gui.layout.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;

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
	 * @param cmd a {@link de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand} object.
	 */
	void setSize(EdgeLayoutCommand cmd);

}
