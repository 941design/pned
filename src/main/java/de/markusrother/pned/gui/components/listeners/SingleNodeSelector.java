package de.markusrother.pned.gui.components.listeners;

import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.SELECT;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.PnEventTarget;
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

	private final PnEventTarget eventTarget;

	/**
	 * <p>
	 * Constructor for SingleNodeSelector.
	 * </p>
	 *
	 * @param eventTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.PnEventTarget}
	 *            object.
	 */
	public SingleNodeSelector(final PnEventTarget eventTarget) {
		super(AbstractNode.class);
		this.eventTarget = eventTarget;
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
			eventTarget.nodeSelectionCancelled(new NodeMultiSelectionEvent(CANCEL, this));
			eventTarget.nodesSelected(new NodeMultiSelectionEvent(SELECT, this, Arrays.asList(node)));
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
