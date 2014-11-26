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
public class SingleNodeSelector extends DragDropListener {

	private AbstractNode expectAbstractNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private void makeCurrentSelection(final AbstractNode node) {
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this));
		eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECT, this, Arrays.asList(node)));
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
