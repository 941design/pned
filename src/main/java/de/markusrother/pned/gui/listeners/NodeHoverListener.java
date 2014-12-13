package de.markusrother.pned.gui.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.swing.HoverAdapter;

/**
 * TODO - subclass for node types
 */
public class NodeHoverListener extends HoverAdapter {

	public static NodeHoverListener INSTANCE = new NodeHoverListener();

	private NodeHoverListener() {
	}

	@Override
	protected void startHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(AbstractNode.State.HOVER);
	}

	@Override
	protected void endHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(AbstractNode.State.DEFAULT);
	}
}
