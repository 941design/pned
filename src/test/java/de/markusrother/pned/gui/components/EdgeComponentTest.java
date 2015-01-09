package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.components.listeners.MarkingEditor;
import de.markusrother.pned.gui.core.EdgeStyle;
import de.markusrother.pned.gui.core.NodeStyle;
import de.markusrother.pned.gui.core.model.MarkingStyleModel;

public class EdgeComponentTest extends AbstractComponentTest<EdgeComponent> {

	private static final String NO_ID = "";

	private Marking createMarking() {
		return new Marking(eventMulticastMock, //
				Mockito.mock(MarkingStyleModel.class));
	}

	@Override
	protected EdgeComponent getComponent() {
		final AbstractNode sourceComponent = new Place(eventMulticastMock, //
				NO_ID, //
				createMarking(), //
				Mockito.mock(MarkingEditor.class), //
				NodeStyle.newDefault());
		final AbstractNode targetComponent = new Transition(eventMulticastMock, //
				NO_ID, //
				NodeStyle.newDefault());
		return new EdgeComponent(eventMulticastMock, //
				NO_ID, //
				EdgeStyle.newDefault(), //
				sourceComponent, //
				targetComponent);
	}

}
