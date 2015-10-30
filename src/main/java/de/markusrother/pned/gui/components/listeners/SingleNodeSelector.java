package de.markusrother.pned.gui.components.listeners;

import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.PnEventTarget;
import de.markusrother.swing.DragDropAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.CANCEL;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.DESELECT;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.FINISH;
import static de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type.SELECT;

/**
 * Creates selection by clicking on a single component.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SingleNodeSelector
        extends DragDropAdapter<AbstractNodeComponent> {

    private final PnEventTarget eventTarget;
    private final Collection<AbstractNodeComponent> nodes = new LinkedList<>();

    /**
     * <p>
     * Constructor for SingleNodeSelector.
     * </p>
     *
     * @param eventTarget a
     *                    {@link de.markusrother.pned.gui.control.events.PnEventTarget}
     *                    object.
     */
    public SingleNodeSelector(final PnEventTarget eventTarget) {
        super(AbstractNodeComponent.class);
        this.eventTarget = eventTarget;
    }

    /**
     * <p>
     * expectAbstractNode.
     * </p>
     *
     * @param component a {@link java.awt.Component} object.
     * @return a
     * {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
     * object.
     */
    private AbstractNodeComponent expectAbstractNode(final Component component) {
        try {
            return (AbstractNodeComponent) component;
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
     * @param node a
     *             {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
     *             object.
     */
    private void makeCurrentSelection(final AbstractNodeComponent node) {
        if (node.isSelected() && !node.isPartOfMultiselection()) {
            // IGNORE - Nothing to do, node is already selected.
        } else {
            // NOTE - Do NOT rely on event status change within this method!
            // We have to cancel other singly selected nodes, too:
            nodes.clear();
            nodes.add(node);
            eventTarget.nodeSelectionCancelled(new NodeMultiSelectionEvent(CANCEL, this));
            eventTarget.nodesSelected(new NodeMultiSelectionEvent(SELECT, this, Arrays.asList(node)));
            eventTarget.nodeSelectionFinished(new NodeMultiSelectionEvent(FINISH, this, Arrays.asList(node)));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClicked(final MouseEvent e) {
        super.mouseClicked(e);
        final AbstractNodeComponent node = expectAbstractNode(e.getComponent());
        if (e.isControlDown()) {
            if (node.isSelected()) {
                nodes.remove(node);
                eventTarget.nodesSelected(new NodeMultiSelectionEvent(DESELECT, this, Arrays.asList(node)));
                eventTarget.nodeSelectionFinished(new NodeMultiSelectionEvent(FINISH, this, nodes));
            } else {
                nodes.add(node);
                eventTarget.nodesSelected(new NodeMultiSelectionEvent(SELECT, this, Arrays.asList(node)));
                eventTarget.nodeSelectionFinished(new NodeMultiSelectionEvent(FINISH, this, nodes));
            }
            return;
        }
        makeCurrentSelection(node);
    }

    /** {@inheritDoc} */
    @Override
    public void startDrag(final AbstractNodeComponent node, final Point dragStart) {
        if (node.isPartOfMultiselection()) {
            // dragging multiselection has precedence
            return;
        }
        makeCurrentSelection(node);
    }

}
