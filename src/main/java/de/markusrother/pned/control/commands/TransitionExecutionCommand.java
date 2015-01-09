package de.markusrother.pned.control.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Instances of this class trigger execution of an active
 * {@link de.markusrother.pned.core.model.TransitionModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionExecutionCommand extends EventObject
	implements
		JsonSerializable {

	private final String transitionId;

	/**
	 * <p>
	 * Constructor for TransitionExecutionCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - this event's source.
	 * @param transitionId
	 *            a {@link java.lang.String} - the unique identifier of the
	 *            transition to be fired.
	 */
	public TransitionExecutionCommand(final Object source, final String transitionId) {
		super(source);
		this.transitionId = transitionId;
	}

	/**
	 * <p>
	 * Getter for the field <code>transitionId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the unique identifier of the
	 *         transition to be fired.
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
		return builder.append("transitionId", transitionId) //
				.toString();
	}

}
