package de.markusrother.pned.gui.components.listeners;

import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.DESELECT;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.FINISH;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.SELECT;

import java.util.Collection;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.PnEventTarget;
import de.markusrother.swing.Selector;

/**
 * <p>
 * NodeSelector class.
 * </p>
 *
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeSelector extends Selector<AbstractNode> {

	private final PnEventTarget eventTarget;

	/**
	 * <p>
	 * Constructor for NodeSelector.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public NodeSelector(final PnEventTarget eventTarget) {
		super(AbstractNode.class);
		this.eventTarget = eventTarget;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Called when starting a new selection by drag and drop movement, canceling
	 * previous selection if any.
	 */
	@Override
	public void startedSelection() {
		eventTarget.nodeSelectionCancelled(new NodeMultiSelectionEvent(CANCEL, this));
	}

	/**
	 * {@inheritDoc}
	 *
	 * Called for every incremental addition of nodes during a drag and drop
	 * operation.
	 */
	@Override
	public void addedToSelection(final Collection<AbstractNode> nodes) {
		eventTarget.nodesSelected(new NodeMultiSelectionEvent(SELECT, this, nodes));
	}

	/**
	 * {@inheritDoc}
	 *
	 * Called for every incremental removal of nodes during a drag and drop
	 * operation.
	 */
	@Override
	public void removedFromSelection(final Collection<AbstractNode> nodes) {
		eventTarget.nodesUnselected(new NodeMultiSelectionEvent(DESELECT, this, nodes));
	}

	/**
	 * {@inheritDoc}
	 *
	 * Called upon finishing the drag and drop operation.
	 */
	@Override
	public void finishedSelection(final Collection<AbstractNode> nodes) {
		eventTarget.nodeSelectionFinished(new NodeMultiSelectionEvent(FINISH, this, nodes));
	}

}
