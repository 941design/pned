package de.markusrother.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Promise<T> {

	public static <U> Promise<U> fulfilled(final U value) {
		final Promise<U> promise = new Promise<>();
		promise.fulfill(value);
		return promise;
	}

	protected T value;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	public Future<T> ask() {

		final Promise<T> that = this;

		return executor.submit(new Callable<T>() {
			@Override
			public T call() {
				synchronized (that) {
					while (that.value == null) {
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

	public synchronized void fulfill(final T value) {
		this.value = value;
		notifyAll();
	}

}
