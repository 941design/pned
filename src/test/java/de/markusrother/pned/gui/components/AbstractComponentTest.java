package de.markusrother.pned.gui.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.control.listeners.EdgeCreationListener;
import de.markusrother.pned.control.listeners.LabelEditListener;
import de.markusrother.pned.control.listeners.NodeCreationListener;
import de.markusrother.pned.control.listeners.NodeMotionListener;
import de.markusrother.pned.control.listeners.PlaceListener;
import de.markusrother.pned.control.listeners.TransitionActivationListener;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PetriNetListener;

// TODO - Maybe rename to AbstractListenerTest
public abstract class AbstractComponentTest<T> {

	private static final Collection<Class<? extends EventListener>> excludedEventListenerInterfaces = new LinkedList<>();
	static {
		excludedEventListenerInterfaces.add(ChangeListener.class);
	}

	protected GuiEventBus eventMulticastMock;

	@Before
	public void setUp() {
		this.eventMulticastMock = Mockito.mock(GuiEventBus.class);
	}

	protected abstract T getComponent();

	public static List<Class<EventListener>> getEventListenerClasses(final Class<?> clazz) {
		final Collection<Class<?>> interfaces = getInterfaces(clazz);
		final List<Class<EventListener>> listenerInterfaces = new LinkedList<>();
		for (final Class<?> iface : interfaces) {
			if (!excludedEventListenerInterfaces.contains(iface) //
					&& EventListener.class.isAssignableFrom(iface) //
					&& iface != EventListener.class //
					&& Arrays.asList(iface.getInterfaces()).contains(EventListener.class)) {
				final @SuppressWarnings("unchecked") Class<EventListener> listenerClass = (Class<EventListener>) iface;
				listenerInterfaces.add(listenerClass);
			}
		}
		return listenerInterfaces;
	}

	public static Collection<Class<?>> getInterfaces(final Class<?> clazz) {
		final List<Class<?>> interfaces = new LinkedList<>();
		Class<? extends Object> type = clazz;
		while (type != null && type != Object.class) {
			final List<Class<?>> ifaces = Arrays.asList(type.getInterfaces());
			interfaces.addAll(ifaces);
			for (final Class<?> iface : ifaces) {
				interfaces.addAll(getInterfaces(iface));
			}
			type = type.getSuperclass();
		}
		return interfaces;
	}

	@Test
	public void testComponentAddsItselfToListeners() {
		final T component = getComponent();
		final List<Class<EventListener>> listenerClasses = getEventListenerClasses(component.getClass());
		for (final Class<EventListener> listenerClass : listenerClasses) {
			final EventListener listener = (EventListener) component;
			Mockito.verify(eventMulticastMock, Mockito.times(1)).addListener(listenerClass, listener);
		}
	}

	@Test
	public void testComponentListensToPetriNet() {
		final T component = getComponent();
		if (component instanceof PetriNetListener) {
			Mockito.verify(eventMulticastMock).addListener(PetriNetListener.class, (PetriNetListener) component);
		}
	}

	@Test
	public void testComponentListensToNode() {
		final T component = getComponent();
		if (component instanceof NodeListener) {
			Mockito.verify(eventMulticastMock).addListener(NodeListener.class, (NodeListener) component);
		}
	}

	@Test
	public void testComponentListensToNodeCreation() {
		final T component = getComponent();
		if (component instanceof NodeCreationListener) {
			Mockito.verify(eventMulticastMock)
					.addListener(NodeCreationListener.class, (NodeCreationListener) component);
		}
	}

	@Test
	public void testComponentListensToNodeSelection() {
		final T component = getComponent();
		if (component instanceof NodeSelectionListener) {
			Mockito.verify(eventMulticastMock).addListener(NodeSelectionListener.class,
					(NodeSelectionListener) component);
		}
	}

	@Test
	public void testComponentListensToNodeMotion() {
		final T component = getComponent();
		if (component instanceof NodeMotionListener) {
			Mockito.verify(eventMulticastMock).addListener(NodeMotionListener.class, (NodeMotionListener) component);
		}
	}

	@Test
	public void testComponentListensToNodeRemoval() {
		final T component = getComponent();
		if (component instanceof NodeRemovalListener) {
			Mockito.verify(eventMulticastMock).addListener(NodeRemovalListener.class, (NodeRemovalListener) component);
		}
	}

	@Test
	public void testComponentListensToPlaceEdit() {
		final T component = getComponent();
		if (component instanceof PlaceListener) {
			Mockito.verify(eventMulticastMock).addListener(PlaceListener.class, (PlaceListener) component);
		}
	}

	@Test
	public void testComponentListensToPlaceLayout() {
		final T component = getComponent();
		if (component instanceof PlaceLayoutListener) {
			Mockito.verify(eventMulticastMock).addListener(PlaceLayoutListener.class, (PlaceLayoutListener) component);
		}
	}

	@Test
	public void testComponentListensToTransitionActivation() {
		final T component = getComponent();
		if (component instanceof TransitionActivationListener) {
			Mockito.verify(eventMulticastMock).addListener(TransitionActivationListener.class,
					(TransitionActivationListener) component);
		}
	}

	@Test
	public void testComponentListensToTransitionLayout() {
		final T component = getComponent();
		if (component instanceof TransitionLayoutListener) {
			Mockito.verify(eventMulticastMock).addListener(TransitionLayoutListener.class,
					(TransitionLayoutListener) component);
		}
	}

	@Test
	public void testComponentListensToEdgeCreation() {
		final T component = getComponent();
		if (component instanceof EdgeCreationListener) {
			Mockito.verify(eventMulticastMock)
					.addListener(EdgeCreationListener.class, (EdgeCreationListener) component);
		}
	}

	@Test
	public void testComponentListensToEdgeEdit() {
		final T component = getComponent();
		if (component instanceof EdgeEditListener) {
			Mockito.verify(eventMulticastMock).addListener(EdgeEditListener.class, (EdgeEditListener) component);
		}
	}

	@Test
	public void testComponentListensToEdgeLayout() {
		final T component = getComponent();
		if (component instanceof EdgeLayoutListener) {
			Mockito.verify(eventMulticastMock).addListener(EdgeLayoutListener.class, (EdgeLayoutListener) component);
		}
	}

	@Test
	public void testComponentListensToMarkingLayout() {
		final T component = getComponent();
		if (component instanceof MarkingLayoutListener) {
			Mockito.verify(eventMulticastMock).addListener(MarkingLayoutListener.class,
					(MarkingLayoutListener) component);
		}
	}

	@Test
	public void testComponentListensToLabelEdit() {
		final T component = getComponent();
		if (component instanceof LabelEditListener) {
			Mockito.verify(eventMulticastMock).addListener(LabelEditListener.class, (LabelEditListener) component);
		}
	}

}
