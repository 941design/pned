package de.markusrother.pned.control.requests;

import java.util.EventListener;

/**
 * <p>
 * IdRequestListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public interface IdRequestListener
    extends
        EventListener {

    /**
     * <p>
     * requestId.
     * </p>
     *
     * @param req
     *            a {@link de.markusrother.pned.control.requests.IdRequest}
     *            object.
     */
    void requestId(IdRequest req);

}
