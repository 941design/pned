package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;

public interface NodeListener extends EventListener {

	void nodeCreated(NodeCreationEvent e);

	void nodeRemoved(NodeRemovalEvent e);

}
