package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.style.MarkingStyle;

public class MarkingTest extends AbstractComponentTest<Marking> {

	@Override
	protected Marking getComponent() {
		return new Marking(eventMulticastMock, MarkingStyle.newDefault());
	}

}
