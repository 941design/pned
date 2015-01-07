package de.markusrother.pned.gui.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * TODO type
 *
 */
public class EdgeRemoveCommand extends EventObject
	implements
		JsonSerializable {

	private final String edgeId;

	public EdgeRemoveCommand(final Object source, final String edgeId) {
		super(source);
		this.edgeId = edgeId;
	}

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
