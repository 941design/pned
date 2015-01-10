package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.components.listeners.NodeLabelEditor;

public class LabelTest extends AbstractComponentTest<LabelComponent> {

	@Override
	protected LabelComponent getComponent() {
		return new LabelComponent(eventMulticastMock, Mockito.mock(NodeLabelEditor.class), null);
	}

}
