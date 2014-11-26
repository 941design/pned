package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.SELECT;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.DragDropListener;

/**
 * Creates selection by clicking on a single component.
 *
 */
public class SingleNodeSelector extends DragDropListener<AbstractNode> {

	private AbstractNode selectedNode;

	public SingleNodeSelector() {
		super(AbstractNode.class);
	}

	private AbstractNode expectAbstractNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private void makeCurrentSelection(final AbstractNode node) {
		if (selectedNode != null && selectedNode == node) {
			return;
		}
		selectedNode = node;
		if (node.isSelected()) {
			// Node may have been selected by another other mechanism, such as
			// multiselection.
			return;
		}
		// Deselection is triggered by cancellation.
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this));
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECT, this, Arrays.asList(selectedNode)));
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
		makeCurrentSelection(expectAbstractNode(e.getComponent()));
	}

	@Override
	public void startDrag(final AbstractNode node, final Point dragStart) {
		// TODO - How can we start dragging without a preceding click?
		makeCurrentSelection(node);
	}

	@Override
	public void onDrag(final AbstractNode node, final int deltaX, final int deltaY) {
		// IGNORE
	}

	@Override
	public void endDrag(final AbstractNode node, final Point dragEnd) {
		// IGNORE
	}

}
