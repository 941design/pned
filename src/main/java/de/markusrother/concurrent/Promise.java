package de.markusrother.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * <p>
 * Placeholder for a value to be set. Intended for asynchronous use, where the
 * value is asked for in one thread and fulfilled in another. The returned value
 * is wrapped in a {@link java.util.concurrent.Future}, blocking no earlier than
 * when {@link java.util.concurrent.Future#get()} is called.
 * </p>
 * <p>
 * A {@link de.markusrother.concurrent.Promise}s value can only be fulfilled once, but asked for
 * arbitrarily.
 * </p>
 *
 * <pre>
 * Promise&lt;String&gt; promise = new Promise&lt;&gt;();
 * // In thread A:
 * Future&lt;String&gt; value = promise.ask();
 * // In thread B:
 * promise.fulfill(&quot;foobar&quot;);
 * </pre>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Promise<T> {

    /**
     * <p>
     * Creates and Returns an already fulfilled {@link de.markusrother.concurrent.Promise}.
     * </p>
     *
     * @param value
     *            a T - the value to be returned.
     * @param <T>
     *            a T - the type of the value to be returned.
     * @return a {@link de.markusrother.concurrent.Promise} object.
     */
    public static <T> Promise<T> fulfilled(final T value) {
        final Promise<T> promise = new Promise<>();
        promise.fulfill(value);
        return promise;
    }

    /** The value to be set and gotten. */
    protected T value;
    /** Whether the value has been set. */
    protected boolean isFulfilled;

    /** The thread pool in which to fork value requests. */
    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * <p>
     * Requests this promises value. This call itself is not blocking, only the
     * call to {@link java.util.concurrent.Future#get()} is.
     * </p>
     *
     * @return a {@link java.util.concurrent.Future} - The future value.
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
     * @return a {@link java.util.concurrent.ExecutorService} - the thread pool
     *         in which to fork value requests.
     */
    protected ExecutorService getExecutor() {
        return executor;
    }

    /**
     * <p>
     * Provides the requested value, causing continuation of waiting threads.
     * </p>
     *
     * @param value
     *            a T object - the value which is asked for.
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
     * Returns whether this {@link de.markusrother.concurrent.Promise} has yet been fulfilled.
     * </p>
     *
     * @return a boolean - true if a value has been provided.
     */
    public synchronized boolean isFulfilled() {
        return isFulfilled;
    }

}
