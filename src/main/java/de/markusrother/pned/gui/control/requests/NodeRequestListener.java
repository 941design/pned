package de.markusrother.pned.gui.control.requests;

import java.util.EventListener;

/**
 * <p>
 * NodeRequestListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeRequestListener
    extends
        EventListener {

    /**
     * <p>
     * requestNode.
     * </p>
     *
     * @param req
     *            a {@link de.markusrother.pned.gui.control.requests.NodeRequest}
     *            object.
     */
    void requestNode(NodeRequest req);

}
