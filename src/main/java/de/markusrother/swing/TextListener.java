package de.markusrother.swing;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;

/**
 * <p>
 * TextListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TextListener {

	/**
	 * <p>
	 * textEntered.
	 * </p>
	 *
	 * @param e
	 *            a {@link java.awt.event.ActionEvent} object.
	 */
	void textEntered(ActionEvent e);

	void cancel(AWTEvent e);

}
