package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;

public interface NodeListener
	extends
		EventListener {

	void nodeCreated(NodeCreationEvent e);

	void nodeRemoved(NodeRemovalEvent e);

	void removeSelectedNodes(RemoveSelectedNodesEvent e);

}
