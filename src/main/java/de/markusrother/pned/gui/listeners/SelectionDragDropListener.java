package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.DragDropListener;

public class SelectionDragDropListener extends DragDropListener<AbstractNode> {

	private final Collection<AbstractNode> nodes;

	public SelectionDragDropListener(final Collection<AbstractNode> nodes) {
		super(AbstractNode.class);
		this.nodes = nodes;
	}

	@Override
	public void startDrag(final AbstractNode draggedNode, final Point dragStart) {
		// IGNORE
	}

	@Override
	public void onDrag(final AbstractNode draggedNode, final int deltaX, final int deltaY) {
		for (final AbstractNode node : nodes) {
			final Rectangle r = node.getBounds();
			r.translate(deltaX, deltaY);
			node.setBounds(r);
			node.repaint();
		}
		eventBus.fireNodeMovedEvent(new NodeMovedEvent(this, nodes, deltaX, deltaY));
	}

	@Override
	public void endDrag(final AbstractNode draggedNode, final Point dragEnd) {
		cancel();
	}

	public void cancel() {
		for (final AbstractNode node : nodes) {
			// TODO - This should be done by the node, too.
			node.removeDragListener(this);
		}
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this));
	}
}
