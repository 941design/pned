package de.markusrother.pned.gui.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.pned.gui.components.NodeLabel;
import de.markusrother.swing.HoverAdapter;

public class LabelHoverListener extends HoverAdapter {

	@Override
	protected void startHover(final Component component) {
		final NodeLabel nodeLabel = (NodeLabel) component;
		nodeLabel.setState(ComponentState.HOVER);
	}

	@Override
	protected void endHover(final Component component) {
		final NodeLabel nodeLabel = (NodeLabel) component;
		nodeLabel.setState(ComponentState.DEFAULT);
	}

}
