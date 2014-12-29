package de.markusrother.pned.gui.components;

public class LabelTest extends AbstractComponentTest<NodeLabel> {

	@Override
	protected NodeLabel getComponent() {
		return new NodeLabel(eventMulticastMock, null);
	}
}
