package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Class that triggers creation of an
 * {@link de.markusrother.pned.core.model.EdgeModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public class EdgeCreationCommand extends EventObject
	implements
		JsonSerializable {

	/** The new edge's unique identifier. */
	private final String edgeId;
	/** The new edge's source node's unique identifier. */
	private final String sourceId;
	/** The new edge's target node's unique identifier. */
	private final String targetId;

	/**
	 * <p>
	 * Constructor for EdgeCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param edgeId
	 *            a {@link java.lang.String} - the new edge's unique identifier.
	 * @param sourceId
	 *            a {@link java.lang.String} - the new edge's source node's
	 *            unique identifier.
	 * @param targetId
	 *            a {@link java.lang.String} -the new edge's target node's
	 *            unique identifier.
	 */
	public EdgeCreationCommand(final Object source, final String edgeId, final String sourceId, final String targetId) {
		super(source);
		this.edgeId = edgeId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	/**
	 * <p>
	 * Getter for the field <code>edgeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the new edge's unique identifier.
	 */
	public String getEdgeId() {
		return edgeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>sourceId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the new edge's source node's unique
	 *         identifier.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * <p>
	 * Getter for the field <code>targetId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the new edge's source node's unique
	 *         identifier..
	 */
	public String getTargetId() {
		return targetId;
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
		return builder.append("edgeId", edgeId) //
				.append("sourceId", sourceId) //
				.append("targetId", targetId) //
				.toString();
	}

}
