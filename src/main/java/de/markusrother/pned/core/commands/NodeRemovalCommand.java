package de.markusrother.pned.core.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * NodeRemovalEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeRemovalCommand extends EventObject
	implements
		JsonSerializable {

	private final String nodeId;

	/**
	 * <p>
	 * Constructor for NodeRemovalEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 */
	public NodeRemovalCommand(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("nodeId", nodeId) //
				.toString();
	}

}
