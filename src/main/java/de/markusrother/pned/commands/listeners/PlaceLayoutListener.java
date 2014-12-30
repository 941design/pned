package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.PlaceLayoutCommand;

/**
 * <p>PlaceLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PlaceLayoutListener
	extends
		EventListener {

	/**
	 * <p>setSize.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.commands.PlaceLayoutCommand} object.
	 */
	void setSize(PlaceLayoutCommand cmd);

}
