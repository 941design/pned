package de.markusrother.pned.core.requests;

import java.util.EventObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;

/**
 * <p>
 * Class of events that expect a 'return' value, usually set by one of the
 * requests recipients. This object encapsulates a callback mechanism with the
 * {@link #set(Object)} method. {@code Request} is intended for asynchronous use
 * only, because its {@link #get()} method is blocking.
 * </p>
 *
 * <pre>
 * request = new Request&lt;String&gt;();
 * eventTarget.post(request);
 * String result = rq.get(); // blocking
 * </pre>
 *
 * where it is assumed that {@code request.set(someString)} is called after the
 * request has been posted.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Request<T> extends EventObject {

	/** The timeout for getting a result. */
	public static final long defaultTimeoutMillis = 500;

	/**
	 * <p>
	 * Constructor for Request.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} - the event's source.
	 */
	protected Request(final Object source) {
		super(source);
		this.promise = new Promise<>();
	}

	/** The pending value holding object. */
	private final Promise<T> promise;

	/**
	 * <p>
	 * Returns this requests 'return' value.
	 * </p>
	 *
	 * @return a T - the actual value.
	 * @throws java.util.concurrent.TimeoutException
	 *             if no value was set until exceeding the
	 *             {@link #defaultTimeoutMillis}.
	 */
	public T get() throws TimeoutException {
		return get(defaultTimeoutMillis, TimeUnit.MILLISECONDS);
	}

	/**
	 * <p>
	 * Returns this requests 'return' value.
	 * </p>
	 *
	 * @return a T - the actual value.
	 * @throws java.util.concurrent.TimeoutException
	 *             if no value was set until exceeding the
	 *             {@link #defaultTimeoutMillis}.
	 */
	public T get(final long timeout, final TimeUnit unit) throws TimeoutException {
		try {
			return promise.ask().get(timeout, unit);
		} catch (InterruptedException | ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	/**
	 * <p>
	 * Sets this requests 'return' value.
	 * </p>
	 *
	 * @param obj
	 *            a T - the value to be set.
	 */
	public void set(final T obj) {
		promise.fulfill(obj);
	}

}
