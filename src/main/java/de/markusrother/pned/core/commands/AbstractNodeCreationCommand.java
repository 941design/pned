package de.markusrother.pned.core.commands;

import java.awt.Point;
import java.util.EventObject;

/**
 * <p>
 * Abstract AbstractNodeCreationCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractNodeCreationCommand extends EventObject {

	private final Point point;
	private final String nodeId;

	/**
	 * <p>
	 * Constructor for AbstractNodeCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
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
	 * @return a {@link java.lang.String} object.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>point</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	public Point getPoint() {
		return point.getLocation();
	}

}
