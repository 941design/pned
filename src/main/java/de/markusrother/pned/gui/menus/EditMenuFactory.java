package de.markusrother.pned.gui.menus;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

/**
 * TODO - maintain state and keep track of number of selected nodes! Depending
 * on number of nodes, the initial menu is enabled or disabled.
 *
 * Factory for edit menus that are sensitive to state changes.
 *
 * TODO - As the menus themselves are singleton, all this class does is translate
 * events to property changes.  This is not entirely necessary, because e.g. the
 * selection removal action needs a reference to the event bus anyways. the singleton 
 * might as well listen to the events itself. We could factor out an event translator,
 * whose only responsibility is to translate events. It would listen to the bus for
 * given events, and simply add the resulting events to the bus again.
 */
public class EditMenuFactory
	implements
		NodeSelectionListener {

	public static final String ARE_NODES_SELECTED = "are nodes selected";

	private final Collection<PropertyChangeListener> listeners;
	private final Set<AbstractNode> selection;

	public EditMenuFactory(final EventBus eventBus) {
		this.listeners = new LinkedList<>();
		this.selection = new HashSet<>();
		eventBus.addNodeSelectionListener(this);
		// TODO - remove upon close
	}

	public PnedEditMenu newEditMenu() {
		final PnedEditMenu menu = new PnedEditMenu();
		listeners.add(menu);
		return menu;
	}

	private boolean areNodesSelected() {
		return !selection.isEmpty();
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		final boolean wereNodesSelected = areNodesSelected();
		selection.addAll(event.getNodes());
		firePropertyChangeEvent(wereNodesSelected, event);
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		final boolean wereNodesSelected = areNodesSelected();
		selection.removeAll(event.getNodes());
		firePropertyChangeEvent(wereNodesSelected, event);
	}

	private void firePropertyChangeEvent(final boolean wereNodesSelected, final NodeSelectionEvent event) {
		firePropertyChangeEvent(new PropertyChangeEvent( //
				event.getSource(), //
				ARE_NODES_SELECTED, //
				wereNodesSelected, //
				areNodesSelected()));
	}

	private void firePropertyChangeEvent(final PropertyChangeEvent e) {
		for (final PropertyChangeListener l : listeners) {
			l.propertyChange(e);
		}
	}

}
