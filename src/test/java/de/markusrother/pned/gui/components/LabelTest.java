package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.listeners.NodeLabelEditor;

public class LabelTest extends AbstractComponentTest<NodeLabel> {

	@Override
	protected NodeLabel getComponent() {
		return new NodeLabel(eventMulticastMock, Mockito.mock(NodeLabelEditor.class), null);
	}

}
