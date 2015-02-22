package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.gui.control.PnEventBus;

/**
 * <p>
 * Event logger that prints all events to {@link java.lang.System#out}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnEventLogger extends PnEventAdapter {

    /**
     * <p>
     * log.
     * </p>
     *
     * @param eventBus
     *            a {@link de.markusrother.pned.gui.control.PnEventBus} object.
     */
    public static void log(final PnEventBus eventBus) {
        final PnEventLogger logger = new PnEventLogger();
        logger.setEventBus(eventBus);
    }

    /** {@inheritDoc} */
    @Override
    protected void process(final EventObject e) {
        log(e);
    }

    /**
     * <p>
     * log.
     * </p>
     *
     * @param e
     *            a {@link java.util.EventObject} object.
     */
    private void log(final EventObject e) {
        System.out.println(e);
    }

}
