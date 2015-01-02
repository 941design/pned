package de.markusrother.pned.core.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * NodeMovedEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeMotionCommand extends EventObject
	implements
		JsonSerializable {

	private final String nodeId;
	private final int deltaY;
	private final int deltaX;

	/**
	 * <p>
	 * Constructor for NodeMovedEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeIds
	 *            a {@link java.lang.String} object.
	 * @param deltaX
	 *            a int.
	 * @param deltaY
	 *            a int.
	 */
	public NodeMotionCommand(final Object source, final String nodeIds, final int deltaX, final int deltaY) {
		// TODO - refactor to single node event!
		// FIXME - Should probably take nodePromise!
		super(source);
		this.nodeId = nodeIds;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
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

	/**
	 * <p>
	 * Getter for the field <code>deltaX</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getDeltaX() {
		return deltaX;
	}

	/**
	 * <p>
	 * Getter for the field <code>deltaY</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getDeltaY() {
		return deltaY;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("nodeId", nodeId) //
				.append("deltaX", deltaX) //
				.append("deltaY", deltaY) //
				.toString();
	}

}
