package de.markusrother.pned.control.requests;

import java.util.EventObject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;

/**
 * <p>
 * Class of events that expect a 'return' value, to be set by one of the
 * requests listeners. Listeners may or may not fulfill this request. However,
 * if none does the request will time out with a
 * {@link java.util.concurrent.TimeoutException}. The responsible listener to
 * this event may set the value with this object's {@link #set(Object)} method.
 * It may be retrieved asynchronously with this object's {@link #set(Object)}
 * method.
 * </p>
 * <p>
 * <b> {@link de.markusrother.pned.control.requests.Request} is intended for asynchronous use only, because its
 * {@link #get()} method is blocking. </b>
 * </p>
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * request = new Request&lt;String&gt;();
 * eventTarget.post(request);
 * String result = rq.get(); // blocking
 * </pre>
 *
 * where it is assumed that {@code request.set(someString)} is called by some
 * responsible instance after the request has been posted to continue.
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public class Request<T> extends EventObject {

    /** The timeout for getting a result. */
    public static final long defaultTimeoutMillis = 5000;

    /**
     * <p>
     * Constructor for Request.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} - this event's source.
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
     * @param timeout
     *            a long.
     * @param unit
     *            a {@link java.util.concurrent.TimeUnit} object.
     */
    public T get(final long timeout, final TimeUnit unit) throws TimeoutException {
        try {
            return promise.ask().get(timeout, unit);
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
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
