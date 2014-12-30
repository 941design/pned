package de.markusrother.pned.gui.events;

import java.util.EventObject;

/**
 * <p>NodeRemovalEvent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeRemovalEvent extends EventObject {

	private final String nodeId;

	/**
	 * <p>Constructor for NodeRemovalEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param nodeId a {@link java.lang.String} object.
	 */
	public NodeRemovalEvent(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	/**
	 * <p>Getter for the field <code>nodeId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNodeId() {
		return nodeId;
	}

}
