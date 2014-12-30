package de.markusrother.pned.core;

import java.awt.Point;

import de.markusrother.util.JsonBuildable;

/**
 * <p>NodeModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeModel
	extends
		JsonBuildable {

	/**
	 * <p>getId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getId();

	/**
	 * <p>hasId.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean hasId(String id);

	/**
	 * <p>getLabel.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLabel();

	/**
	 * <p>setLabel.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 */
	void setLabel(String label);

	/**
	 * <p>getPosition.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	Point getPosition();

	/**
	 * <p>setPosition.</p>
	 *
	 * @param origin a {@link java.awt.Point} object.
	 */
	void setPosition(Point origin);

	/**
	 * <p>move.</p>
	 *
	 * @param deltaX a int.
	 * @param deltaY a int.
	 */
	void move(int deltaX, int deltaY);

}
