package de.markusrother.pned.util;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.gui.components.AbstractComponentTest;
import de.markusrother.pned.gui.control.GuiEventBus;

public class PetriNetGuiEventAdapterTest {

	@Test
	public void testComponentAddsItselfToListeners() {
		final GuiEventBus eventBus = Mockito.mock(GuiEventBus.class);
		final PetriNetGuiEventAdapter adapter = new PetriNetGuiEventAdapter() {
			@Override
			protected void process(final EventObject e) {
				// IGNORE
			}
		};
		adapter.setEventBus(eventBus);
		final List<Class<EventListener>> listenerClasses = AbstractComponentTest.getEventListenerClasses(adapter
				.getClass());
		for (final Class<EventListener> listenerClass : listenerClasses) {
			Mockito.verify(eventBus, Mockito.times(1)).addListener(listenerClass, adapter);
		}
	}

}
