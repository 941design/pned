package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.LabelComponent;

/**
 * <p>
 * LabelRequest class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.requests.LabelRequestListener
 * @see de.markusrother.pned.gui.components.LabelComponent#requestLabel(LabelRequest)
 */
public class LabelRequest extends Request<LabelComponent> {

    /**
     * <p>
     * doRequestLabel.
     * </p>
     *
     * @param nodeId
     *            a {@link java.lang.String} object.
     * @param requestTarget
     *            a
     *            {@link de.markusrother.pned.gui.control.requests.PnRequestTarget}
     *            object.
     * @return a {@link de.markusrother.pned.gui.components.LabelComponent}
     *         object.
     */
    public static LabelComponent doRequestLabel(final String nodeId, final PnRequestTarget requestTarget) {
        try {
            final LabelRequest req = new LabelRequest(LabelRequest.class, nodeId);
            requestTarget.requestLabel(req);
            final LabelComponent label = req.get();
            return label;
        } catch (final TimeoutException e) {
            throw new RuntimeException("TODO", e);
        }
    }

    private final String nodeId;

    /**
     * <p>
     * Constructor for LabelRequest.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} object.
     * @param nodeId
     *            a {@link java.lang.String} object.
     */
    public LabelRequest(final Object source, final String nodeId) {
        super(source);
        this.nodeId = nodeId;
    }

    /**
     * <p>
     * createWorker.
     * </p>
     *
     * @param l
     *            a
     *            {@link de.markusrother.pned.gui.control.requests.LabelRequestListener}
     *            object.
     * @return a {@link javax.swing.SwingWorker} object.
     */
    public SwingWorker<LabelComponent, Object> createWorker(final LabelRequestListener l) {
        return new LabelRequestWorker(this, l);
    }

    /**
     * <p>
     * Getter for the field <code>nodeId</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNodeId() {
        return nodeId;
    }

}
