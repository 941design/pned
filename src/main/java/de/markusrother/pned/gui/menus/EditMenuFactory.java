package de.markusrother.pned.gui.menus;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

/**
 * TODO - maintain state and keep track of number of selected nodes! Depending
 * on number of nodes, the initial menu is enabled or disabled.
 *
 * Factory for edit menus that are sensitive to state changes.
 *
 * TODO - As the menus themselves are singleton, all this class does is
 * translate events to property changes. This is not entirely necessary, because
 * e.g. the selection removal action needs a reference to the event bus anyways.
 * the singleton might as well listen to the events itself. We could factor out
 * an event translator, whose only responsibility is to translate events. It
 * would listen to the bus for given events, and simply add the resulting events
 * to the bus again.
 */
public class EditMenuFactory
	implements
		NodeSelectionListener {

	private boolean areNodesSelected;
	private final NodeCreationMode nodeCreationMode;

	public EditMenuFactory(final EventBus eventBus) {
		this.areNodesSelected = false;
		this.nodeCreationMode = NodeCreationMode.defaultCreationMode;
		eventBus.addNodeSelectionListener(this);
		// TODO - remove upon close
	}

	public PnedEditMenu newEditMenu() {
		return new PnedEditMenu(areNodesSelected, nodeCreationMode);
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// IGNORE
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// IGNORE
	}

	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		areNodesSelected = !event.getNodes().isEmpty();
	}

	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		areNodesSelected = false;
	}

}
