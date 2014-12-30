package de.markusrother.pned.core;

import de.markusrother.util.JsonBuildable;

/**
 * <p>EdgeModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeModel
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
	 * @param edgeId a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean hasId(String edgeId);

	/**
	 * <p>getSourceId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSourceId();

	/**
	 * <p>setSourceId.</p>
	 *
	 * @param sourceId a {@link java.lang.String} object.
	 */
	void setSourceId(String sourceId);

	/**
	 * <p>getTargetId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTargetId();

	/**
	 * <p>setTargetId.</p>
	 *
	 * @param targetId a {@link java.lang.String} object.
	 */
	void setTargetId(String targetId);

}
