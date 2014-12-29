package de.markusrother.pned.gui.components;


public class EdgeComponentTest extends AbstractComponentTest<EdgeComponent> {

	@Override
	protected EdgeComponent getComponent() {
		final AbstractNode sourceComponent = new Place(0);
		final AbstractNode targetComponent = new Transition(0);
		return new EdgeComponent(sourceComponent, targetComponent);
	}

}
