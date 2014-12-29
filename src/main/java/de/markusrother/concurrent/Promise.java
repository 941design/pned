package de.markusrother.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class Promise<T> {

	public static <U> Promise<U> fulfilled(final U value) {
		final Promise<U> promise = new Promise<>();
		promise.fulfill(value);
		return promise;
	}

	protected T value;
	protected boolean isFulfilled;

	private final ExecutorService executor = Executors.newCachedThreadPool();

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

	protected ExecutorService getExecutor() {
		return executor;
	}

	public synchronized void fulfill(final T value) {
		if (this.isFulfilled) {
			throw new RejectedExecutionException("Promise already fulfilled");
		}
		this.isFulfilled = true;
		this.value = value;
		notifyAll();
	}

	public synchronized boolean isFulfilled() {
		return isFulfilled;
	}

}
