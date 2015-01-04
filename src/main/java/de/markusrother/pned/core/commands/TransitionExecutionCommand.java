package de.markusrother.pned.core.commands;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>TransitionExecutionCommand class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionExecutionCommand extends EventObject
	implements
		JsonSerializable {

	private final String transitionId;

	/**
	 * <p>Constructor for TransitionExecutionCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param transitionId a {@link java.lang.String} object.
	 */
	public TransitionExecutionCommand(final Object source, final String transitionId) {
		super(source);
		this.transitionId = transitionId;
	}

	/**
	 * <p>Getter for the field <code>transitionId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTransitionId() {
		return transitionId;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("transitionId", transitionId) //
				.toString();
	}

}
