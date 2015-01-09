package de.markusrother.pned.gui.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * TODO type
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeRemoveCommand extends EventObject
	implements
		JsonSerializable {

	private final String edgeId;

	/**
	 * <p>Constructor for EdgeRemoveCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param edgeId a {@link java.lang.String} object.
	 */
	public EdgeRemoveCommand(final Object source, final String edgeId) {
		super(source);
		this.edgeId = edgeId;
	}

	/**
	 * <p>Getter for the field <code>edgeId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getEdgeId() {
		return edgeId;
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
				.toString();
	}

}
