package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Instances of this class cause removal of
 * {@link de.markusrother.pned.core.model.NodeModel}s.
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
	 *            a {@link java.lang.Object} - this event's source.
	 * @param nodeId
	 *            a {@link java.lang.String} - the unique identifier of the node
	 *            to be removed.
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
	 * @return a {@link java.lang.String} - the unique identifier of the node to
	 *         be removed.
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
