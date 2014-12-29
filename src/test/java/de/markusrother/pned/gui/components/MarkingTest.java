package de.markusrother.pned.gui.components;

public class MarkingTest extends AbstractComponentTest<Marking> {

	@Override
	protected Marking getComponent() {
		return new Marking(eventMulticastMock);
	}

}
