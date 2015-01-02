package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.gui.control.GuiEventBus;

/**
 * <p>PetriNetGuiEventLogger class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetGuiEventLogger extends PetriNetGuiEventAdapter {

	/**
	 * <p>log.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	public static void log(final GuiEventBus eventBus) {
		final PetriNetGuiEventLogger logger = new PetriNetGuiEventLogger();
		logger.setEventBus(eventBus);
	}

	/** {@inheritDoc} */
	@Override
	protected void process(final EventObject e) {
		log(e);
	}

	private void log(final EventObject e) {
		System.out.println(e);
	}

}
