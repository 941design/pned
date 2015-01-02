package de.markusrother.pned.core.events;

import java.util.EventObject;

/**
 * <p>
 * TransitionActivationEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionActivationEvent extends EventObject {

	public enum Type {
		ACTIVATION,
		DEACTIVATION
	}

	private final Type type;
	private final String transitionId;

	/**
	 * <p>
	 * Constructor for TransitionActivationEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.core.events.TransitionActivationEvent.Type}
	 *            object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param transitionId
	 *            a {@link java.lang.String} object.
	 */
	public TransitionActivationEvent(final Type type, final Object source, final String transitionId) {
		super(source);
		this.type = type;
		this.transitionId = transitionId;
	}

	/**
	 * <p>
	 * Getter for the field <code>type</code>.
	 * </p>
	 *
	 * @return a
	 *         {@link de.markusrother.pned.core.events.TransitionActivationEvent.Type}
	 *         object.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <p>
	 * Getter for the field <code>transitionId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTransitionId() {
		return transitionId;
	}

}
