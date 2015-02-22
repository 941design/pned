package de.markusrother.pned.control.events;

import java.util.EventListener;

/**
 * <p>
 * MarkingEventListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface MarkingEventListener
    extends
        EventListener {

    /**
     * <p>
     * setMarking.
     * </p>
     *
     * @param evt
     *            a
     *            {@link de.markusrother.pned.control.events.MarkingChangeEvent}
     *            object.
     */
    void setMarking(MarkingChangeEvent evt);

}
