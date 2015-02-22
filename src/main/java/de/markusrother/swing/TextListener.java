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

    /**
     * <p>cancel.</p>
     *
     * @param e a {@link java.awt.AWTEvent} object.
     */
    void cancel(AWTEvent e);

}
