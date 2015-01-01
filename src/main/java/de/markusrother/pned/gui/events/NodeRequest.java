package de.markusrother.pned.gui.events;

import de.markusrother.pned.core.events.Request;
import de.markusrother.pned.gui.components.AbstractNode;

/**
 * <p>NodeRequest class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeRequest extends Request<AbstractNode> {

	private final String nodeId;

	/**
	 * <p>Constructor for NodeRequest.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param nodeId a {@link java.lang.String} object.
	 */
	public NodeRequest(final Object source, final String nodeId) {
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
