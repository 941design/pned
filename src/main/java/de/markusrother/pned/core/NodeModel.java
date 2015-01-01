package de.markusrother.pned.core;

import java.awt.Point;

/**
 * <p>
 * Mutable model for Petri net nodes. Nodes consist of a unique, immutable
 * identifier, and mutable label and coordinates.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeModel {

	/**
	 * <p>
	 * Returns this nodes's unique identifier.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the id.
	 */
	String getId();

	/**
	 * <p>
	 * Returns true if given identifier equals this node's identifier.
	 * </p>
	 * <p>
	 * Implementors should use the {@link java.lang.String#equals} comparison.
	 * </p>
	 *
	 * @param id
	 *            a {@link java.lang.String} - the id to compare against.
	 * @return a boolean - the comparison's result.
	 */
	boolean hasId(String id);

	/**
	 * <p>
	 * Returns this node's current label.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the label.
	 */
	String getLabel();

	/**
	 * <p>
	 * Sets this node's label.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} - the new label.
	 */
	void setLabel(String label);

	/**
	 * <p>
	 * Returns this node's current coordinates.
	 * </p>
	 *
	 * @return a {@link java.awt.Point} object - the coordinates.
	 */
	Point getPosition();

	/**
	 * <p>
	 * Sets this node's coordinates
	 * </p>
	 *
	 * @param origin
	 *            a {@link java.awt.Point} object - the new coordinates.
	 */
	void setPosition(Point origin);

	/**
	 * <p>
	 * Adjusts this node's coordinates by the given deltas.
	 * </p>
	 *
	 * @param deltaX
	 *            a int - the relative horizontal change.
	 * @param deltaY
	 *            a int - the relative vertical change.
	 */
	void move(int deltaX, int deltaY);

}
