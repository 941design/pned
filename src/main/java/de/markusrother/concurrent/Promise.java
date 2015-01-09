package de.markusrother.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * <p>
 * Promise class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Promise<T> {

	/**
	 * <p>
	 * fulfilled.
	 * </p>
	 *
	 * @param value
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 * @return a {@link de.markusrother.concurrent.Promise} object.
	 */
	public static <T> Promise<T> fulfilled(final T value) {
		final Promise<T> promise = new Promise<>();
		promise.fulfill(value);
		return promise;
	}

	protected T value;
	protected boolean isFulfilled;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * <p>
	 * ask.
	 * </p>
	 *
	 * @return a {@link java.util.concurrent.Future} object.
	 */
	public Future<T> ask() {

		final Promise<T> that = this;

		return getExecutor().submit(new Callable<T>() {
			@Override
			public T call() {
				synchronized (that) {
					while (!that.isFulfilled) {
						try {
							that.wait();
						} catch (final InterruptedException e) {
							// IGNORE
						}
					}
				}
				return value;
			}
		});
	}

	/**
	 * <p>
	 * Getter for the field <code>executor</code>.
	 * </p>
	 *
	 * @return a {@link java.util.concurrent.ExecutorService} object.
	 */
	protected ExecutorService getExecutor() {
		return executor;
	}

	/**
	 * <p>
	 * fulfill.
	 * </p>
	 *
	 * @param value
	 *            a T object.
	 */
	public synchronized void fulfill(final T value) {
		if (this.isFulfilled) {
			throw new RejectedExecutionException("Promise already fulfilled");
		}
		this.isFulfilled = true;
		this.value = value;
		notifyAll();
	}

	/**
	 * <p>
	 * isFulfilled.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public synchronized boolean isFulfilled() {
		return isFulfilled;
	}

}
