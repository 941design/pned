package de.markusrother.pned.gui.components.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.swing.HoverAdapter;

/**
 * @author Markus Rother
 * @version 1.0
 */
public class NodeHoverListener extends HoverAdapter {

	/** Constant <code>INSTANCE</code> */
	public static NodeHoverListener INSTANCE = new NodeHoverListener();

	/**
	 * <p>
	 * Constructor for NodeHoverListener.
	 * </p>
	 */
	private NodeHoverListener() {
	}

	/** {@inheritDoc} */
	@Override
	protected void startHover(final Component component) {
		final AbstractNodeComponent node = (AbstractNodeComponent) component;
		node.setState(ComponentState.HOVER);
	}

	/** {@inheritDoc} */
	@Override
	protected void endHover(final Component component) {
		final AbstractNodeComponent node = (AbstractNodeComponent) component;
		node.setState(ComponentState.DEFAULT);
	}

}
