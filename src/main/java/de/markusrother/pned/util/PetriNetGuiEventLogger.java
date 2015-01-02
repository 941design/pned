package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.gui.control.GuiEventBus;

public class PetriNetGuiEventLogger extends PetriNetGuiEventAdapter {

	public static void log(final GuiEventBus eventBus) {
		final PetriNetGuiEventLogger logger = new PetriNetGuiEventLogger();
		logger.setEventBus(eventBus);
	}

	@Override
	protected void process(final EventObject e) {
		log(e);
	}

	private void log(final EventObject e) {
		System.out.println(e);
	}

}
