package de.markusrother.pned.control.events;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Event that occurs when a
 * {@link de.markusrother.pned.core.model.TransitionModel} is activated due to
 * previous change in the {@link de.markusrother.pned.core.model.PetriNetModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public class TransitionActivationEvent extends EventObject
	implements
		JsonSerializable {

	/**
	 * The type of
	 * {@link de.markusrother.pned.control.events.TransitionActivationEvent}.
	 */
	public enum Type {
		/** Indicates activation of a transition. */
		ACTIVATION,
		/** Indicates deactivation of a transition. */
		DEACTIVATION
	}

	/** The type of change. */
	private final Type type;
	/** The unique identifier of the transition that changed. */
	private final String transitionId;

	/**
	 * <p>
	 * Constructor for TransitionActivationEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.control.events.TransitionActivationEvent.Type}
	 *            - the type of change.
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param transitionId
	 *            a {@link java.lang.String} - the unique identifier of the
	 *            transition that changed.
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
	 *         {@link de.markusrother.pned.control.events.TransitionActivationEvent.Type}
	 *         - the type of change.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <p>
	 * Getter for the field <code>transitionId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the unique identifier of the
	 *         transition that changed.
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
		return builder.append("source", source.getClass().getSimpleName()) //
				.append("type", type.name()) //
				.append("transitionId", transitionId) //
				.toString();
	}

}
