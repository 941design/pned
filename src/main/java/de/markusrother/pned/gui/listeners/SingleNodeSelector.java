package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.events.NodeSelectionEvent.Type.SELECT;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.DragDropListener;

/**
 * Creates selection by clicking on a single component.
 *
 * mousePressed is not sufficient, because we want to distinguish behavior of
 * click vs. drag.
 *
 * TODO - make nice!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SingleNodeSelector extends DragDropListener<AbstractNode> {

	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * Constructor for SingleNodeSelector.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.events.EventBus} object.
	 */
	public SingleNodeSelector(final GuiEventBus eventBus) {
		super(AbstractNode.class);
		this.eventBus = eventBus;
	}

	/**
	 * <p>
	 * expectAbstractNode.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *         object.
	 */
	private AbstractNode expectAbstractNode(final Component component) {
		try {
			return (AbstractNode) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	/**
	 * <p>
	 * makeCurrentSelection.
	 * </p>
	 *
	 * @param node
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 */
	private void makeCurrentSelection(final AbstractNode node) {
		// Must not return if node is part of multiselection!
		// No events when reselecting the already selected node.
		// Node may have been selected by another other mechanism, such as
		// multiselection. Although we would not have to unselect the given
		// node, we must invoke cancellation of all nodes to deselect the other
		// selected nodes.
		if (node.isSelected() && !node.isPartOfMultiselection()) {
			// IGNORE - Nothing to do, node is already selected.
		} else {
			// NOTE - Do NOT rely on event status change within this method!
			// We have to cancel other singly selected nodes, too:
			eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(CANCEL, this));
			eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECT, this, Arrays.asList(node)));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
		makeCurrentSelection(expectAbstractNode(e.getComponent()));
	}

	/** {@inheritDoc} */
	@Override
	public void startDrag(final AbstractNode node, final Point dragStart) {
		// If mouse is pressed down on unselected node, we receive a startDrag
		// (based on mouseDragged) without the preceding mouseClicked!
		// We need to activate the selection for the nodes actual DragDrop
		// listener!
		if (node.isPartOfMultiselection()) {
			// dragging multiselection has precedence
			return;
		}
		makeCurrentSelection(node);
	}

	/** {@inheritDoc} */
	@Override
	public void onDrag(final AbstractNode node, final int deltaX, final int deltaY) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void endDrag(final AbstractNode node, final Point dragEnd) {
		// IGNORE
	}

}
