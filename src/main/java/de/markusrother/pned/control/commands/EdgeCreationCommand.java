package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * EdgeCreationCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeCreationCommand extends EventObject
	implements
		JsonSerializable {

	private final String edgeId;
	private final String sourceId;
	private final String targetId;

	/**
	 * <p>
	 * Constructor for EdgeCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param edgeId
	 *            a {@link java.lang.String} object.
	 * @param sourceId
	 *            a {@link java.lang.String} object.
	 * @param targetId
	 *            a {@link java.lang.String} object.
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
	 * @return a {@link java.lang.String} object.
	 */
	public String getEdgeId() {
		return edgeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>sourceId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * <p>
	 * Getter for the field <code>targetId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
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
