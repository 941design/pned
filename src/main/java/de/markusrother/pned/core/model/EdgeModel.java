package de.markusrother.pned.core.model;

/**
 * <p>
 * Mutable model for Petri net edges. Edges consist of a unique, immutable
 * identifier, and mutable source and target identifiers.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeModel {

	/**
	 * <p>
	 * Returns this edge's unique identifier.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the id.
	 */
	String getId();

	/**
	 * <p>
	 * Returns true if given identifier equals this edge's identifier.
	 * </p>
	 * <p>
	 * Implementors should use the {@link java.lang.String#equals} comparison.
	 * </p>
	 *
	 * @param edgeId
	 *            a {@link java.lang.String} - the id to compare against.
	 * @return a boolean - the comparison's result.
	 */
	boolean hasId(String edgeId);

	/**
	 * <p>
	 * Returns this edge's source node's unique identifier.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the source node's id.
	 */
	String getSourceId();

	/**
	 * <p>
	 * Sets this edge's source node's unique identifier.
	 * </p>
	 *
	 * @param sourceId
	 *            a {@link java.lang.String} the source node's new id.
	 */
	void setSourceId(String sourceId);

	/**
	 * <p>
	 * Returns this edge's target node's unique identifier
	 * </p>
	 *
	 * @return a {@link java.lang.String} the target node's id.
	 */
	String getTargetId();

	/**
	 * <p>
	 * Sets this edge's target node's unique identifier
	 * </p>
	 *
	 * @param targetId
	 *            a {@link java.lang.String} the target node's new id.
	 */
	void setTargetId(String targetId);

}
