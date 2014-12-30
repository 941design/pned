package de.markusrother.pned.gui.events;

import java.util.EventObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;

public class Request<T> extends EventObject {

	protected Request(final Object source) {
		super(source);
		this.promise = new Promise<>();
	}

	private final Promise<T> promise;

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

	public void set(final T obj) {
		promise.fulfill(obj);
	}

}
