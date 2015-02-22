package de.markusrother.pned.control.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

/**
 * <p>
 * A closure executing an {@link de.markusrother.pned.control.requests.IdRequest} for a given
 * {@link de.markusrother.pned.control.requests.IdRequestListener}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
class IdRequestWorker extends SwingWorker<String, Object> {

    /** The request to work on. */
    private final IdRequest request;
    /** The listener doing the work. */
    private final IdRequestListener listener;

    /**
     * <p>
     * Constructor for IdRequestWorker.
     * </p>
     *
     * @param request
     *            a {@link de.markusrother.pned.control.requests.IdRequest} -
     *            the request to work on.
     * @param listener
     *            a
     *            {@link de.markusrother.pned.control.requests.IdRequestListener}
     *            - the listener doing the work.
     */
    public IdRequestWorker(final IdRequest request, final IdRequestListener listener) {
        this.request = request;
        this.listener = listener;
    }

    /** {@inheritDoc} */
    @Override
    protected String doInBackground() throws TimeoutException {
        listener.requestId(request);
        return request.get();
    }

    /** {@inheritDoc} */
    @Override
    protected void done() {
        try {
            get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            // TODO - use custom exception
            throw new RuntimeException("TODO");
        }
    }

}
