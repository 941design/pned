package de.markusrother.pned.util;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.gui.components.AbstractComponentTest;

public class PetriNetEventAdapterTest {

	@Test
	public void testComponentAddsItselfToListeners() {
		final EventBus eventBus = Mockito.mock(EventBus.class);
		final PetriNetEventAdapter adapter = new PetriNetEventAdapter() {
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
