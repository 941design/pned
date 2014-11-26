package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.DESELECT;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.FINISH;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.SELECT;

import java.util.Collection;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.Selector;

// Now we have a selection, and need to do something with it. Obtaining
// the selection, altered global state. Many other actions have to
// respect that we are now in selection mode, e.g. starting another, new
// selection, cancelling selection by clicking somewhere. The latter
// event is NOT likely to bubble back to this selection.
// From this point of view, a number of unknown components, or even more
// general objects exist, that may cancel the selection. The decision to
// cancel a selection created here should also remain here. The question
// then is, how to receive those possible cancellation events without
// coupling all components with each other.

// FIXME - Create SelectionListener(nodes), which should fire the
// same drag event for each node in the selection as the one used
// for single drag! Currently, the Abstract nodes are still wrapped
// inside snap target components.
// TODO - The entire grid needs to be selection aware, because we
// may want to click anywhere in the non-selected area to release
// the selection. Maybe that should go to the model.
// @see EventListenerList

// I could also create a MouseAdapter for nodes which manages state
// and does not forward events unless state criteria is met! I LIKE
// THIS. override addMouseListener, and andMouseMotionListener in
// T to intercept or create custom methods. Switch case
// would make state handling concentrated, reducing coupling, and
// generally quite nice. Just add listeners like crazy and let the
// responsible instance take care of it. The event bus could be a
// singleton.
public class NodeSelector extends Selector<AbstractNode> {

	public NodeSelector() {
		super(AbstractNode.class);
	}

	/**
	 * Called when starting a new selection by drag and drop movement, canceling
	 * previous selection if any.
	 */
	@Override
	public void startedSelection() {
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this));
	}

	/**
	 * Called for every incremental addition of nodes during a drag and drop
	 * operation.
	 * 
	 * @param nodes
	 *            Collection of nodes that were just added to the current
	 *            selection.
	 */
	@Override
	public void addedToSelection(final Collection<AbstractNode> nodes) {
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECT, this, nodes));
	}

	/**
	 * Called for every incremental removal of nodes during a drag and drop
	 * operation.
	 * 
	 * @param nodes
	 *            Collection of nodes that were just removed from the current
	 *            selection.
	 */
	@Override
	public void removedFromSelection(final Collection<AbstractNode> nodes) {
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(DESELECT, this, nodes));
	}

	/**
	 * Called upon finishing the drag and drop operation.
	 * 
	 * @param nodes
	 *            Collection of all selected nodes.
	 */
	@Override
	public void finishedSelection(final Collection<AbstractNode> items) {
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(FINISH, this));
	}

}
