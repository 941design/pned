package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

public class NodeRemovalEvent extends ActionEvent {

	private final String nodeId;

	public NodeRemovalEvent(final Object source, final String nodeId) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

}
