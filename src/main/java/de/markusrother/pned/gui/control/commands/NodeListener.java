package de.markusrother.pned.gui.control.commands;

import java.util.EventListener;

/**
 * <p>NodeListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeListener
    extends
        EventListener {

    /**
     * <p>setCurrentNodeType.</p>
     *
     * @param cmd a {@link de.markusrother.pned.gui.control.commands.SetNodeTypeCommand} object.
     */
    void setCurrentNodeType(SetNodeTypeCommand cmd);

}
