package de.markusrother.pned.gui.components;

import org.mockito.Mockito;

import de.markusrother.pned.gui.menus.PnEditorMenuFactory;

public class PnGridPanelTest extends AbstractComponentTest<PnGridPanel> {

	@Override
	protected PnGridPanel getComponent() {
		return new PnGridPanel(eventMulticastMock, Mockito.mock(PnEditorMenuFactory.class));
	}

}
