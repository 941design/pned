package de.markusrother.pned.gui.layout.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;

/**
 * <p>
 * PlaceLayoutListener interface.
 * </p>
 * 
 * FIXME rename these to GuiPlaceListener
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
	 *            {@link de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand}
	 *            object.
	 */
	void setSize(PlaceLayoutCommand cmd);

}
