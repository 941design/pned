package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.LabelEditEvent;

/**
 * <p>LabelEditListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface LabelEditListener
	extends
		EventListener {

	/**
	 * <p>setLabel.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.LabelEditEvent} object.
	 */
	void setLabel(LabelEditEvent e);

}
