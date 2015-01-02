package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.gui.control.GuiEventBus;

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

	public static void log(final GuiEventBus eventBus) {
		final PetriNetEventLogger logger = new PetriNetEventLogger();
		logger.setEventBus(eventBus);
	}

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
