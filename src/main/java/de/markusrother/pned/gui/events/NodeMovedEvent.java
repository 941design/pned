package de.markusrother.pned.gui.events;

import java.util.Collection;
import java.util.EventObject;

/**
 * <p>NodeMovedEvent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeMovedEvent extends EventObject {

	private final Collection<String> nodeIds;
	private final int deltaY;
	private final int deltaX;

	/**
	 * <p>Constructor for NodeMovedEvent.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param nodeIds a {@link java.util.Collection} object.
	 * @param deltaX a int.
	 * @param deltaY a int.
	 */
	public NodeMovedEvent(final Object source, final Collection<String> nodeIds, final int deltaX, final int deltaY) {
		// TODO - refactor to single node event!
		// FIXME - Should probably take nodePromise!
		super(source);
		this.nodeIds = nodeIds;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	/**
	 * <p>Getter for the field <code>nodeIds</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getNodeIds() {
		return nodeIds;
	}

	/**
	 * <p>Getter for the field <code>deltaX</code>.</p>
	 *
	 * @return a int.
	 */
	public int getDeltaX() {
		return deltaX;
	}

	/**
	 * <p>Getter for the field <code>deltaY</code>.</p>
	 *
	 * @return a int.
	 */
	public int getDeltaY() {
		return deltaY;
	}

}
