package de.markusrother.pned.gui.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.pned.gui.components.NodeLabel;
import de.markusrother.swing.HoverAdapter;

/**
 * <p>LabelHoverListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelHoverListener extends HoverAdapter {

	/** {@inheritDoc} */
	@Override
	protected void startHover(final Component component) {
		final NodeLabel nodeLabel = (NodeLabel) component;
		nodeLabel.setState(ComponentState.HOVER);
	}

	/** {@inheritDoc} */
	@Override
	protected void endHover(final Component component) {
		final NodeLabel nodeLabel = (NodeLabel) component;
		nodeLabel.setState(ComponentState.DEFAULT);
	}

}
