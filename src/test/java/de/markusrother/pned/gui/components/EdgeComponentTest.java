package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.layout.style.NodeStyle;
import de.markusrother.pned.gui.listeners.MarkingEditor;

public class EdgeComponentTest extends AbstractComponentTest<EdgeComponent> {

	private static final String NO_ID = "";

	@Override
	protected EdgeComponent getComponent() {
		final AbstractNode sourceComponent = new Place(eventMulticastMock, //
				NO_ID, //
				Mockito.mock(MarkingEditor.class), //
				NodeStyle.DEFAULT);
		final AbstractNode targetComponent = new Transition(eventMulticastMock, //
				NO_ID, //
				NodeStyle.DEFAULT);
		return new EdgeComponent(eventMulticastMock, NO_ID, sourceComponent, targetComponent);
	}

}
