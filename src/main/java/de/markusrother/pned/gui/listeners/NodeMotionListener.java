package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeMovedEvent;

public interface NodeMotionListener extends EventListener {

	public void nodeMoved(NodeMovedEvent nodeMovedEvent);

}
