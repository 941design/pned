package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.DragDropListener;

/**
 * TODO - rename (node specific)
 */
public class SelectionDragDropListener extends DragDropListener<AbstractNode> {

	private final Collection<AbstractNode> nodes;
	private final EventBus eventBus;

	public SelectionDragDropListener(final EventBus eventBus, final Collection<AbstractNode> nodes) {
		super(AbstractNode.class);
		this.eventBus = eventBus;
		this.nodes = nodes;
	}

	@Override
	public void startDrag(final AbstractNode draggedNode, final Point dragStart) {
		// IGNORE
	}

	@Override
	public void onDrag(final AbstractNode draggedNode, final int deltaX, final int deltaY) {
		final Collection<String> nodeIds = new LinkedList<>();
		for (final AbstractNode node : nodes) {
			nodeIds.add(node.getId());
		}
		eventBus.nodeMoved(new NodeMovedEvent(this, nodeIds, deltaX, deltaY));
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
