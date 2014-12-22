package de.markusrother.pned.core;

import java.util.EventObject;

public class TransitionActivationEvent extends EventObject {

	public enum Type {
		ACTIVATION,
		DEACTIVATION
	}

	private final Type type;
	private final String transitionId;

	public TransitionActivationEvent(final Type type, final Object source, final String transitionId) {
		super(source);
		this.type = type;
		this.transitionId = transitionId;
	}

	public Type getType() {
		return type;
	}

	public String getTransitionId() {
		return transitionId;
	}

}
