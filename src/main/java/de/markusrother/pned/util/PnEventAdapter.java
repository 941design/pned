package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.LabelEditListener;
import de.markusrother.pned.control.commands.MarkingEditCommand;
import de.markusrother.pned.control.commands.MarkingEditListener;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOListener;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.commands.TransitionListener;
import de.markusrother.pned.control.events.MarkingChangeEvent;
import de.markusrother.pned.control.events.MarkingEventListener;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.events.TransitionActivationListener;
import de.markusrother.pned.control.requests.IdRequest;
import de.markusrother.pned.control.requests.IdRequestListener;
import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.control.commands.EdgeLayoutListener;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.control.commands.MarkingLayoutListener;
import de.markusrother.pned.gui.control.commands.NodeListener;
import de.markusrother.pned.gui.control.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.control.commands.PetriNetListener;
import de.markusrother.pned.gui.control.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.control.commands.PlaceLayoutListener;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.control.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.control.commands.TransitionLayoutListener;
import de.markusrother.pned.gui.control.events.EdgeEditEvent;
import de.markusrother.pned.gui.control.events.EdgeEditListener;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.NodeSelectionListener;
import de.markusrother.pned.gui.control.events.PnEventTarget;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.control.requests.EdgeRequest;
import de.markusrother.pned.gui.control.requests.EdgeRequestListener;
import de.markusrother.pned.gui.control.requests.LabelRequest;
import de.markusrother.pned.gui.control.requests.LabelRequestListener;
import de.markusrother.pned.gui.control.requests.NodeRequest;
import de.markusrother.pned.gui.control.requests.NodeRequestListener;
import de.markusrother.pned.gui.control.requests.PnRequestTarget;

/**
 * <p>
 * Adapter that directs all commands, events, and request to a single method:
 * {@link #process(EventObject)}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PnEventAdapter
    implements
        PnCommandTarget,
        PnEventTarget,
        PnRequestTarget {

    /**
     * The event target which is listened to.
     */
    protected PnEventBus eventBus;

    /**
     * <p>
     * Setter for the field <code>eventBus</code>.
     * </p>
     *
     * @param eventBus
     *            a {@link de.markusrother.pned.gui.control.PnEventBus} to which
     *            resulting events are posted to and to which is listened to for
     *            state changes.
     */
    public void setEventBus(final PnEventBus eventBus) {
        if (this.eventBus != null) {
            suspendListeners();
        }
        this.eventBus = eventBus;
        installListeners();
    }

    /**
     * <p>
     * installListeners.
     * </p>
     */
    protected void installListeners() {
        eventBus.addListener(PetriNetIOListener.class, this);
        eventBus.addListener(PetriNetListener.class, this);
        eventBus.addListener(NodeListener.class, this);
        eventBus.addListener(NodeRemovalListener.class, this);
        eventBus.addListener(NodeCreationListener.class, this);
        eventBus.addListener(NodeMotionListener.class, this);
        eventBus.addListener(MarkingEditListener.class, this);
        eventBus.addListener(MarkingEventListener.class, this);
        eventBus.addListener(EdgeEditListener.class, this);
        eventBus.addListener(NodeRequestListener.class, this);
        eventBus.addListener(PlaceLayoutListener.class, this);
        eventBus.addListener(LabelEditListener.class, this);
        eventBus.addListener(LabelRequestListener.class, this);
        eventBus.addListener(TransitionListener.class, this);
        eventBus.addListener(TransitionActivationListener.class, this);
        eventBus.addListener(TransitionLayoutListener.class, this);
        eventBus.addListener(EdgeLayoutListener.class, this);
        eventBus.addListener(MarkingLayoutListener.class, this);
        eventBus.addListener(NodeSelectionListener.class, this);
        eventBus.addListener(IdRequestListener.class, this);
        eventBus.addListener(EdgeCreationListener.class, this);
        eventBus.addListener(EdgeRequestListener.class, this);
    }

    /**
     * <p>
     * suspendListeners.
     * </p>
     */
    protected void suspendListeners() {
        // TODO
        throw new RuntimeException("TODO");
    }

    /** {@inheritDoc} */
    @Override
    public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void nodesSelected(final NodeMultiSelectionEvent event) {
        process(event);
    }

    /** {@inheritDoc} */
    @Override
    public void nodesUnselected(final NodeMultiSelectionEvent event) {
        process(event);
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionFinished(final NodeMultiSelectionEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionCancelled(final NodeMultiSelectionEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void componentEntered(final EdgeEditEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void componentExited(final EdgeEditEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void edgeCancelled(final EdgeEditEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void edgeFinished(final EdgeEditEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void edgeStarted(final EdgeEditEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final PlaceLayoutCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final TransitionLayoutCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final EdgeLayoutCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final MarkingLayoutCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void createPetriNet(final PetriNetEditCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void requestNode(final NodeRequest req) {
        process(req);
    }

    /** {@inheritDoc} */
    @Override
    public void requestLabel(final LabelRequest req) {
        process(req);
    }

    /** {@inheritDoc} */
    @Override
    public void requestEdge(final EdgeRequest req) {
        process(req);
    }

    /** {@inheritDoc} */
    @Override
    public void setCurrentDirectory(final PetriNetIOCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void createPlace(final PlaceCreationCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void createTransition(final TransitionCreationCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void createEdge(final EdgeCreationCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void removeEdge(final EdgeRemoveCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void nodeMoved(final NodeMotionCommand e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void nodeRemoved(final NodeRemovalCommand e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void setMarking(final MarkingChangeEvent evt) {
        process(evt);
    }

    /** {@inheritDoc} */
    @Override
    public void setMarking(final MarkingEditCommand evt) {
        process(evt);
    }

    /** {@inheritDoc} */
    @Override
    public void setLabel(final LabelEditCommand e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void transitionActivated(final TransitionActivationEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void transitionDeactivated(final TransitionActivationEvent e) {
        process(e);
    }

    /** {@inheritDoc} */
    @Override
    public void importPnml(final PetriNetIOCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void exportPnml(final PetriNetIOCommand cmd) {
        process(cmd);
    }

    /** {@inheritDoc} */
    @Override
    public void requestId(final IdRequest req) {
        process(req);
    }

    /** {@inheritDoc} */
    @Override
    public void fireTransition(final TransitionExecutionCommand cmd) {
        process(cmd);
    }

    /**
     * <p>
     * process.
     * </p>
     *
     * @param e
     *            a {@link java.util.EventObject} object.
     */
    protected abstract void process(final EventObject e);

}
