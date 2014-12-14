package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;

public interface NodeRemovalListener
	extends
		EventListener {

	// TODO - What is the difference, exactly?

	void nodeRemoved(NodeRemovalEvent e);

	void removeSelectedNodes(RemoveSelectedNodesEvent e);

}
