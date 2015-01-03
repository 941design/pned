package de.markusrother.pned.core.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

public class TransitionExecutionCommand extends EventObject
	implements
		JsonSerializable {

	private final String transitionId;

	public TransitionExecutionCommand(final Object source, final String transitionId) {
		super(source);
		this.transitionId = transitionId;
	}

	public String getTransitionId() {
		return transitionId;
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("transitionId", transitionId) //
				.toString();
	}

}
