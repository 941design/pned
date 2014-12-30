package de.markusrother.pned.gui.events;

import java.util.EventObject;

public class NodeRemovalEvent extends EventObject {

	private final String nodeId;

	public NodeRemovalEvent(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

}
