package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.NodeSelectionEvent.Type.DESELECT;
import static de.markusrother.pned.gui.NodeSelectionEvent.Type.SELECT;
import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import de.markusrother.swing.DragDropListener;

public class SingleNodeSelector extends DragDropListener {

	private AbstractNode currentNode;

	private AbstractNode expectAbstractNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private void makeCurrentSelection(final AbstractNode node) {
		if (currentNode != null) {
			eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(DESELECT, this, Arrays.asList(currentNode)));
		}
		currentNode = node;
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECT, this, Arrays.asList(currentNode)));
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
		makeCurrentSelection(expectAbstractNode(e.getComponent()));
	}

	@Override
	public void startDrag(final Component component, final Point dragStart) {
		makeCurrentSelection(expectAbstractNode(component));
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		// IGNORE
	}

	@Override
	public void endDrag(final Component component, final Point dragEnd) {
		// IGNORE
	}

}
