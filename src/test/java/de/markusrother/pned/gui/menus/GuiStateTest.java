package de.markusrother.pned.gui.menus;

import java.util.EventListener;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.gui.components.AbstractComponentTest;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.PnState;

public class GuiStateTest {

    @Test
    public void testAllListenersAdded() {
        final PnEventBus eventBus = Mockito.mock(PnEventBus.class);
        final PnState state = new PnState(eventBus);
        final List<Class<EventListener>> listenerClasses = AbstractComponentTest.getEventListenerClasses(state
                .getClass());
        for (final Class<EventListener> listenerClass : listenerClasses) {
            Mockito.verify(eventBus, Mockito.times(1)).addListener(listenerClass, state);
        }
    }

}
