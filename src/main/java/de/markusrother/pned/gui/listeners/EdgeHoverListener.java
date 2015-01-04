package de.markusrother.pned.gui.listeners;

import java.awt.Component;
import java.awt.Point;

import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.swing.HoverListener;

public class EdgeHoverListener extends HoverListener {

	/** Constant <code>INSTANCE</code> */
	public static EdgeHoverListener INSTANCE = new EdgeHoverListener();

	private EdgeHoverListener() {
	}

	@Override
	protected boolean inHoverArea(final Component component, final Point p) {
		final EdgeComponent edge = (EdgeComponent) component;
		return edge.edgeContains(p);
	}

	@Override
	protected void startHover(final Component component) {
		final EdgeComponent edge = (EdgeComponent) component;
		edge.setState(ComponentState.HOVER);
	}

	@Override
	protected void endHover(final Component component) {
		final EdgeComponent edge = (EdgeComponent) component;
		edge.setState(ComponentState.DEFAULT);
	}

}
