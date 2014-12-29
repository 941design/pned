package de.markusrother.pned.gui.components;

public class EdgeComponentTest extends AbstractComponentTest<EdgeComponent> {

	@Override
	protected EdgeComponent getComponent() {
		final AbstractNode sourceComponent = new Place(eventMulticastMock, 0);
		final AbstractNode targetComponent = new Transition(eventMulticastMock, 0);
		return new EdgeComponent(eventMulticastMock, sourceComponent, targetComponent);
	}

}
