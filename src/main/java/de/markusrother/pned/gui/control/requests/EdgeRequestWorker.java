package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.EdgeComponent;

/**
 * <p>EdgeRequestWorker class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeRequestWorker extends SwingWorker<EdgeComponent, Object> {

    private final EdgeRequest request;
    private final EdgeRequestListener listener;

    /**
     * <p>Constructor for EdgeRequestWorker.</p>
     *
     * @param request a {@link de.markusrother.pned.gui.control.requests.EdgeRequest} object.
     * @param listener a {@link de.markusrother.pned.gui.control.requests.EdgeRequestListener} object.
     */
    public EdgeRequestWorker(final EdgeRequest request, final EdgeRequestListener listener) {
        this.request = request;
        this.listener = listener;
    }

    /** {@inheritDoc} */
    @Override
    protected EdgeComponent doInBackground() throws TimeoutException {
        listener.requestEdge(request);
        return request.get();
    }

}
