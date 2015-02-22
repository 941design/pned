package de.markusrother.pned.gui.control.commands;

import java.util.EventListener;

/**
 * <p>TransitionLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface TransitionLayoutListener
    extends
        EventListener {

    /**
     * <p>setSize.</p>
     *
     * @param cmd a {@link de.markusrother.pned.gui.control.commands.TransitionLayoutCommand} object.
     */
    void setSize(TransitionLayoutCommand cmd);
}
