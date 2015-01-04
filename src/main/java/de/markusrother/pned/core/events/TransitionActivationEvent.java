package de.markusrother.pned.core.events;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * TransitionActivationEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionActivationEvent extends EventObject
	implements
		JsonSerializable {

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
	public TransitionActivationEvent(final Object source, final Type type, final String transitionId) {
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("type", type.name()) //
				.append("transitionId", transitionId) //
				.toString();
	}

}
