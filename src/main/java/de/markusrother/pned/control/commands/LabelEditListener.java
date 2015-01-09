package de.markusrother.pned.control.commands;

import java.util.EventListener;

/**
 * <p>
 * LabelEditListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface LabelEditListener
	extends
		EventListener {

	/**
	 * <p>
	 * setLabel.
	 * </p>
	 *
	 * @param cmd
	 *            a
	 *            {@link de.markusrother.pned.control.commands.LabelEditCommand}
	 *            object.
	 */
	void setLabel(LabelEditCommand cmd);

}
