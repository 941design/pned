package de.markusrother.pned.gui.events;

import java.util.EventObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;

/**
 * <p>Request class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Request<T> extends EventObject {

	/**
	 * <p>Constructor for Request.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 */
	protected Request(final Object source) {
		super(source);
		this.promise = new Promise<>();
	}

	private final Promise<T> promise;

	/**
	 * <p>get.</p>
	 *
	 * @return a T object.
	 */
	public T get() {
		try {
			return promise.ask().get(500, TimeUnit.MILLISECONDS);
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (InterruptedException | ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	/**
	 * <p>set.</p>
	 *
	 * @param obj a T object.
	 */
	public void set(final T obj) {
		promise.fulfill(obj);
	}

}
