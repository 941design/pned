package de.markusrother.pned.gui.components;

public class PnGridPanelTest extends AbstractComponentTest<PnGridPanel> {

	@Override
	protected PnGridPanel getComponent() {
		return new PnGridPanel(eventMulticastMock);
	}

}
