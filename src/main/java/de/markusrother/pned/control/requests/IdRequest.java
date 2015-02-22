package de.markusrother.pned.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * An asynchronous request for a new unique identifier. Listeners may or may not
 * fulfill this request. However, if none does the request will time out with a
 * {@link java.util.concurrent.TimeoutException}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.requests.IdRequestListener
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
     *            a {@link java.lang.Object} - this event's source.
     */
    public IdRequest(final Object source) {
        super(source);
    }

    /**
     * <p>
     * Creates a new {@link javax.swing.SwingWorker} for a given listener.
     * </p>
     *
     * @param l
     *            a
     *            {@link de.markusrother.pned.control.requests.IdRequestListener}
     *            - a listener which may or may not fulfill this request.
     * @return a {@link javax.swing.SwingWorker} - to execute this request.
     */
    public SwingWorker<String, Object> createWorker(final IdRequestListener l) {
        return new IdRequestWorker(this, l);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ':' + toJson();
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
