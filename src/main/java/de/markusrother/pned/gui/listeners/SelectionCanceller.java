package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Collections;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeSelectionEvent;

public class SelectionCanceller extends MouseAdapter {

	private static final Collection<AbstractNode> NO_NODES = Collections.emptyList();

	@Override
	public void mouseClicked(final MouseEvent e) {
		// TODO - Currently, user must click twice (first, to deselect, secondly
		// to create node)
		// nodeSelectionDragListener.cancel();
		// TODO - rename to NodeSelectionCommand
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this, NO_NODES));
	}
}
