package de.markusrother.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Promise<T> {

	protected T value;

	private final ExecutorService executor = Executors.newCachedThreadPool();

	public Future<T> get() {

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

	public synchronized void set(final T value) {
		this.value = value;
		notifyAll();
	}

}
