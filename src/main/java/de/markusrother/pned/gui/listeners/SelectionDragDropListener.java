package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;

import java.awt.Point;
import java.util.Collection;

import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.DragDropListener;

/**
 * TODO - rename (node specific)
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SelectionDragDropListener extends DragDropListener<AbstractNode> {

	private final Collection<AbstractNode> nodes;
	// FIXME - GuiCommandTarget should suffice
	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * Constructor for SelectionDragDropListener.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param nodes
	 *            a {@link java.util.Collection} object.
	 */
	public SelectionDragDropListener(final GuiEventBus eventBus, final Collection<AbstractNode> nodes) {
		super(AbstractNode.class);
		this.eventBus = eventBus;
		this.nodes = nodes;
	}

	/** {@inheritDoc} */
	@Override
	public void startDrag(final AbstractNode draggedNode, final Point dragStart) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void onDrag(final AbstractNode draggedNode, final int deltaX, final int deltaY) {
		for (final AbstractNode node : nodes) {
			eventBus.nodeMoved(new NodeMotionCommand(this, node.getId(), deltaX, deltaY));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void endDrag(final AbstractNode draggedNode, final Point dragEnd) {
		cancel();
	}

	/**
	 * <p>
	 * cancel.
	 * </p>
	 */
	public void cancel() {
		for (final AbstractNode node : nodes) {
			// TODO - This should be done by the node, too.
			node.removeDragListener(this);
		}
		eventBus.nodeSelectionCancelled(new NodeSelectionEvent(CANCEL, this));
	}

}
