package de.markusrother.pned.gui.control.requests;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.LabelComponent;

/**
 * <p>LabelRequestWorker class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelRequestWorker extends SwingWorker<LabelComponent, Object> {

    private final LabelRequest request;
    private final LabelRequestListener listener;

    /**
     * <p>Constructor for LabelRequestWorker.</p>
     *
     * @param request a {@link de.markusrother.pned.gui.control.requests.LabelRequest} object.
     * @param listener a {@link de.markusrother.pned.gui.control.requests.LabelRequestListener} object.
     */
    public LabelRequestWorker(final LabelRequest request, final LabelRequestListener listener) {
        this.request = request;
        this.listener = listener;
    }

    /** {@inheritDoc} */
    @Override
    protected LabelComponent doInBackground() throws Exception {
        listener.requestLabel(request);
        return request.get();
    }

}
