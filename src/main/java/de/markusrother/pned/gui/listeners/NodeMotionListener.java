package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeMovedEvent;

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
	 * @param e a {@link de.markusrother.pned.gui.events.NodeMovedEvent} object.
	 */
	public void nodeMoved(NodeMovedEvent e);

}
