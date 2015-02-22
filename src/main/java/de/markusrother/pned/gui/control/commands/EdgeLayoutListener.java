package de.markusrother.pned.gui.control.commands;

import java.util.EventListener;

/**
 * <p>EdgeLayoutListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeLayoutListener
    extends
        EventListener {

    /**
     * <p>setSize.</p>
     *
     * @param cmd a {@link de.markusrother.pned.gui.control.commands.EdgeLayoutCommand} object.
     */
    void setSize(EdgeLayoutCommand cmd);

}
