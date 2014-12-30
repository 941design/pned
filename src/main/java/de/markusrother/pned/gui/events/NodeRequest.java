package de.markusrother.pned.gui.events;

import de.markusrother.pned.gui.components.AbstractNode;

public class NodeRequest extends Request<AbstractNode> {

	private final String nodeId;

	public NodeRequest(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

}
