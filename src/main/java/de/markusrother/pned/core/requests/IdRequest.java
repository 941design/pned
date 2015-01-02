package de.markusrother.pned.core.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.listeners.IdRequestListener;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * IdRequest class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class IdRequest extends Request<String>
	implements

		JsonSerializable {
	/**
	 * <p>
	 * Constructor for IdRequest.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 */
	public IdRequest(final Object source) {
		super(source);
	}

	/**
	 * <p>
	 * createWorker.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.core.listeners.IdRequestListener}
	 *            object.
	 * @return a {@link javax.swing.SwingWorker} object.
	 */
	public SwingWorker<String, Object> createWorker(final IdRequestListener l) {
		return new IdRequestWorker(this, l);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		try {
			final JsonBuilder builder = new JsonBuilder();
			return builder.append("id", get()) //
					.toString();
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
