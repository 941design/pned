package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.commands.SetNodeTypeCommand;

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
	 * @param cmd a {@link de.markusrother.pned.gui.commands.SetNodeTypeCommand} object.
	 */
	void setCurrentNodeType(SetNodeTypeCommand cmd);

}
