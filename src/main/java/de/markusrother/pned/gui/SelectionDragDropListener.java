package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.NodeSelectionEvent.Type.UNSELECTED;
import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import de.markusrother.swing.DragDropListener;

public class SelectionDragDropListener extends DragDropListener {

	public static void addToComponents(final Collection<AbstractNode> nodes, final SelectionDragDropListener listener) {
		for (final AbstractNode node : nodes) {
			DragDropListener.addToComponent(node, listener);
		}
	}

	private final Collection<AbstractNode> nodes;

	private Point absStart;

	public SelectionDragDropListener(final Collection<AbstractNode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public void startDrag(final Component component, final Point point) {
		absStart = component.getLocation();
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		for (final AbstractNode node : nodes) {
			final Rectangle r = node.getBounds();
			r.translate(deltaX, deltaY);
			node.setBounds(r);
			node.repaint();
		}
	}

	@Override
	public void endDrag(final Component component, final Point dragEnd) {
		final Point absEnd = component.getLocation();
		final Point delta = PnGridPanel.delta(absEnd, absStart);
		// TODO - after all, we may want to fire constantly...
		eventBus.fireNodeMovedEvent(new NodeMovedEvent(this, nodes, delta.x, delta.y));
		cancel();
	}

	public void cancel() {
		for (final AbstractNode node : nodes) {
			DragDropListener.removeFromComponent(node, this);
		}
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(UNSELECTED, this, nodes));
	}
}
