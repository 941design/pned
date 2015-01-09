package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.menus.PnMenuFactory;

public class PnGridPanelTest extends AbstractComponentTest<PnGridPanel> {

	@Override
	protected PnGridPanel getComponent() {
		return new PnGridPanel(eventMulticastMock, //
				Mockito.mock(PnMenuFactory.class), //
				Mockito.mock(NodeFactory.class), //
				Mockito.mock(EdgeFactory.class));
	}

}
