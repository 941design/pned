package de.markusrother.pned.gui.components;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.markusrother.pned.commands.listeners.EdgeLayoutListener;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.commands.listeners.TransitionActivationListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

public abstract class AbstractComponentTest<T> {

	protected EventBus eventMulticastMock;

	@Before
	public void setUp() {
		this.eventMulticastMock = Mockito.mock(EventBus.class);
		PnGridPanel.eventBus = this.eventMulticastMock;
	}

	protected abstract T getComponent();

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
		if (component instanceof PlaceEditListener) {
			Mockito.verify(eventMulticastMock).addListener(PlaceEditListener.class, (PlaceEditListener) component);
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
