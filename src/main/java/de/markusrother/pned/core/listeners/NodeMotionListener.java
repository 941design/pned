package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.NodeMotionCommand;

/**
 * <p>NodeMotionListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeMotionListener
	extends
		EventListener {

	/**
	 * <p>nodeMoved.</p>
	 *
	 * @param e a {@link de.markusrother.pned.core.commands.NodeMotionCommand} object.
	 */
	public void nodeMoved(NodeMotionCommand e);

}
