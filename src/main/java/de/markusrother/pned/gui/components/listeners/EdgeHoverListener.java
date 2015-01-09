package de.markusrother.pned.gui.components.listeners;

import java.awt.Component;
import java.awt.Point;

import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.swing.HoverListener;

/**
 * <p>EdgeHoverListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeHoverListener extends HoverListener {

	/** Constant <code>INSTANCE</code> */
	public static EdgeHoverListener INSTANCE = new EdgeHoverListener();

	/**
	 * <p>Constructor for EdgeHoverListener.</p>
	 */
	private EdgeHoverListener() {
	}

	/** {@inheritDoc} */
	@Override
	protected boolean inHoverArea(final Component component, final Point p) {
		final EdgeComponent edge = (EdgeComponent) component;
		return edge.edgeContains(p);
	}

	/** {@inheritDoc} */
	@Override
	protected void startHover(final Component component) {
		final EdgeComponent edge = (EdgeComponent) component;
		edge.setState(ComponentState.HOVER);
	}

	/** {@inheritDoc} */
	@Override
	protected void endHover(final Component component) {
		final EdgeComponent edge = (EdgeComponent) component;
		edge.setState(ComponentState.DEFAULT);
	}

}
