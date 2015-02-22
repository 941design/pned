package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.core.MarkingStyle;

public class MarkingTest extends AbstractComponentTest<MarkingComponent> {

    @Override
    protected MarkingComponent getComponent() {
        return new MarkingComponent(eventMulticastMock, MarkingStyle.newDefault());
    }

}
