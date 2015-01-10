package de.markusrother.pned.gui.components.listeners;

import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.CANCEL;

import java.awt.Point;
import java.util.Collection;

import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.swing.DragDropListener;

/**
 * <p>SelectionDragDropListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SelectionDragDropListener extends DragDropListener<AbstractNodeComponent> {

	private final Collection<AbstractNodeComponent> nodes;
	private final PnEventBus eventBus;

	/**
	 * <p>
	 * Constructor for SelectionDragDropListener.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param nodes
	 *            a {@link java.util.Collection} object.
	 */
	public SelectionDragDropListener(final PnEventBus eventBus, final Collection<AbstractNodeComponent> nodes) {
		super(AbstractNodeComponent.class);
		this.eventBus = eventBus;
		this.nodes = nodes;
	}

	/** {@inheritDoc} */
	@Override
	public void startDrag(final AbstractNodeComponent draggedNode, final Point dragStart) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void onDrag(final AbstractNodeComponent draggedNode, final int deltaX, final int deltaY) {
		for (final AbstractNodeComponent node : nodes) {
			eventBus.nodeMoved(new NodeMotionCommand(this, node.getId(), deltaX, deltaY));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void endDrag(final AbstractNodeComponent draggedNode, final Point dragEnd) {
		cancel();
	}

	/**
	 * <p>
	 * cancel.
	 * </p>
	 */
	public void cancel() {
		for (final AbstractNodeComponent node : nodes) {
			// TODO - This should be done by the node, listening to the cancel
			// event.
			node.removeDragListener(this);
		}
		eventBus.nodeSelectionCancelled(new NodeMultiSelectionEvent(CANCEL, this));
	}

}
