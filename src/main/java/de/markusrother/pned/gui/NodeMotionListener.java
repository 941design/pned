package de.markusrother.pned.gui;

import java.util.EventListener;

public interface NodeMotionListener extends EventListener {

	public void nodeMoved(NodeMovedEvent nodeMovedEvent);

}
