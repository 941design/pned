package de.markusrother.pned.gui.commands;

import java.util.EventListener;

/**
 * <p>
 * MarkingLayoutListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface MarkingLayoutListener
	extends
		EventListener {

	/**
	 * <p>
	 * setSize.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.gui.commands.MarkingLayoutCommand}
	 *            object.
	 */
	void setSize(MarkingLayoutCommand cmd);

}
