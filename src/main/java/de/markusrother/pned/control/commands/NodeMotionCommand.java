package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Instances of this class cause
 * {@link de.markusrother.pned.core.model.NodeModel} movement.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeMotionCommand extends EventObject
	implements
		JsonSerializable {

	/** The unique identifier of the node to be moved. */
	private final String nodeId;
	/** The horizontal change. */
	private final int deltaX;
	/** The vertical change. */
	private final int deltaY;

	/**
	 * <p>
	 * Constructor for NodeMovedEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} - the unique identifier of the node
	 *            to be moved.
	 * @param deltaX
	 *            a int - the horizontal change..
	 * @param deltaY
	 *            a int - - the vertical change.
	 */
	public NodeMotionCommand(final Object source, final String nodeId, final int deltaX, final int deltaY) {
		super(source);
		this.nodeId = nodeId;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the unique identifier of the node to
	 *         be moved.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>deltaX</code>.
	 * </p>
	 *
	 * @return a int - the horizontal change.
	 */
	public int getDeltaX() {
		return deltaX;
	}

	/**
	 * <p>
	 * Getter for the field <code>deltaY</code>.
	 * </p>
	 *
	 * @return a int - the vertical change.
	 */
	public int getDeltaY() {
		return deltaY;
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
				.append("deltaX", deltaX) //
				.append("deltaY", deltaY) //
				.toString();
	}

}
