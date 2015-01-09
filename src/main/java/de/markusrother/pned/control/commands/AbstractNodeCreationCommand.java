package de.markusrother.pned.control.commands;

import java.awt.Point;
import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Abstract superclass for commands that trigger creation of
 * {@link de.markusrother.pned.core.model.NodeModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public abstract class AbstractNodeCreationCommand extends EventObject
	implements
		JsonSerializable {

	/** The new node's unique identifier. */
	protected final String nodeId;
	/** The location at which the new node is to be created. */
	protected final Point point;

	/**
	 * <p>
	 * Constructor for AbstractNodeCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param nodeId
	 *            a {@link java.lang.String} - the new node's unique identifier.
	 * @param point
	 *            a {@link java.awt.Point} - the new node's location.
	 */
	public AbstractNodeCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source);
		this.nodeId = nodeId;
		this.point = point;
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the new node's unique identifier.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>point</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Point} - the new node's location.
	 */
	public Point getPoint() {
		return point.getLocation();
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
		return builder.append("source", source.getClass().getSimpleName()) //
				.append("nodeId", nodeId) //
				.append("x", point.x) //
				.append("y", point.y) //
				.toString();
	}

}
