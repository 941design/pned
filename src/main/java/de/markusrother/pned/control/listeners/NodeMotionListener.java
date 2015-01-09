package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.NodeMotionCommand;

/**
 * <p>
 * NodeMotionListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface NodeMotionListener
	extends
		EventListener {

	/**
	 * <p>
	 * nodeMoved.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.control.commands.NodeMotionCommand}
	 *            object.
	 */
	public void nodeMoved(NodeMotionCommand e);

}
