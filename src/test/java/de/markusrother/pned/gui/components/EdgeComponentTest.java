package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.listeners.MarkingEditor;

public class EdgeComponentTest extends AbstractComponentTest<EdgeComponent> {

	@Override
	protected EdgeComponent getComponent() {
		final AbstractNode sourceComponent = new Place(eventMulticastMock, Mockito.mock(MarkingEditor.class), 0);
		final AbstractNode targetComponent = new Transition(eventMulticastMock, 0);
		return new EdgeComponent(eventMulticastMock, sourceComponent, targetComponent);
	}

}
