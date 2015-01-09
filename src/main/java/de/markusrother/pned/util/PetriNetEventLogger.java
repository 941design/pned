package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.control.EventBus;

/**
 * <p>
 * PetriNetEventLogger class.
 * </p>
 *
 * FIXME - use compound interfaces
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetEventLogger extends PetriNetEventAdapter {

	/**
	 * <p>log.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public static void log(final EventBus eventBus) {
		final PetriNetEventLogger logger = new PetriNetEventLogger();
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
	 * @param event
	 *            a {@link java.util.EventObject} object.
	 */
	protected void log(final EventObject event) {
		System.out.println(event);
	}

}
