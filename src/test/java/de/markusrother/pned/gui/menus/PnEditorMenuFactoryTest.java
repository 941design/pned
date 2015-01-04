package de.markusrother.pned.gui.menus;

import java.util.EventListener;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.gui.components.AbstractComponentTest;
import de.markusrother.pned.gui.control.GuiEventBus;

public class PnEditorMenuFactoryTest {

	@Test
	public void testAllListenersAdded() {
		final GuiEventBus eventBus = Mockito.mock(GuiEventBus.class);
		final PnEditorMenuFactory menuFactory = new PnEditorMenuFactory(eventBus);
		final List<Class<EventListener>> listenerClasses = AbstractComponentTest.getEventListenerClasses(menuFactory
				.getClass());
		for (final Class<EventListener> listenerClass : listenerClasses) {
			Mockito.verify(eventBus, Mockito.times(1)).addListener(listenerClass, menuFactory);
		}
	}

}
