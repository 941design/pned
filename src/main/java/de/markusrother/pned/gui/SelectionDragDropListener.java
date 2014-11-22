package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.NodeSelectionEvent.Type.DESELECT;
import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import de.markusrother.swing.DragDropListener;

public class SelectionDragDropListener extends DragDropListener {

	private final Collection<AbstractNode> nodes;

	public SelectionDragDropListener(final Collection<AbstractNode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public void startDrag(final Component component, final Point dragStart) {
		// IGNORE
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		for (final AbstractNode node : nodes) {
			final Rectangle r = node.getBounds();
			r.translate(deltaX, deltaY);
			node.setBounds(r);
			node.repaint();
		}
		eventBus.fireNodeMovedEvent(new NodeMovedEvent(this, nodes, deltaX, deltaY));
	}

	@Override
	public void endDrag(final Component component, final Point dragEnd) {
		cancel();
	}

	public void cancel() {
		for (final AbstractNode node : nodes) {
			node.removeDragListener(this);
		}
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(DESELECT, this, nodes));
	}
}
