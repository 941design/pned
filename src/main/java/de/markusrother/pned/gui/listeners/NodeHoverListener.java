package de.markusrother.pned.gui.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.swing.HoverAdapter;

/**
 * TODO - subclass for node types
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeHoverListener extends HoverAdapter {

	/** Constant <code>INSTANCE</code> */
	public static NodeHoverListener INSTANCE = new NodeHoverListener();

	/**
	 * <p>Constructor for NodeHoverListener.</p>
	 */
	private NodeHoverListener() {
	}

	/** {@inheritDoc} */
	@Override
	protected void startHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(ComponentState.HOVER);
	}

	/** {@inheritDoc} */
	@Override
	protected void endHover(final Component component) {
		final AbstractNode node = (AbstractNode) component;
		node.setState(ComponentState.DEFAULT);
	}
}
