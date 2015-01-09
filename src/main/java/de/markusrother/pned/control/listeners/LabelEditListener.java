package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.LabelEditCommand;

/**
 * <p>
 * LabelEditListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
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
	 *            a {@link de.markusrother.pned.control.commands.LabelEditCommand}
	 *            object.
	 */
	void setLabel(LabelEditCommand cmd);

}
