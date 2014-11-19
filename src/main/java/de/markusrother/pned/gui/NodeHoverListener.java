package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.Point;

import de.markusrother.swing.HoverListener;

/**
 * TODO - subclass for node types
 */
public class NodeHoverListener extends HoverListener {

	public static NodeHoverListener INSTANCE = new NodeHoverListener();

	private NodeHoverListener() {
	}

	@Override
	protected void startHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(AbstractNode.State.HOVER);
	}

	@Override
	protected boolean inHoverArea(final Point p) {
		return true;
	}

	@Override
	protected void endHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(AbstractNode.State.DEFAULT);
	}
}
