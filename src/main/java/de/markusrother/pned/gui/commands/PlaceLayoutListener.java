package de.markusrother.pned.gui.commands;

import java.util.EventListener;

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
	 *            {@link de.markusrother.pned.gui.commands.PlaceLayoutCommand}
	 *            object.
	 */
	void setSize(PlaceLayoutCommand cmd);

}
