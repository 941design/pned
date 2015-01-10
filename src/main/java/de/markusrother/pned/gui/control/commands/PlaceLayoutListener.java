package de.markusrother.pned.gui.control.commands;

import java.util.EventListener;

/**
 * <p>
 * PlaceLayoutListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PlaceLayoutListener
	extends
		EventListener {

	/**
	 * <p>
	 * setSize.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PlaceLayoutCommand}
	 *            object.
	 */
	void setSize(PlaceLayoutCommand cmd);

}
