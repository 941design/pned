package de.markusrother.pned.gui.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.gui.control.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.control.commands.EdgeLayoutListener;
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
import de.markusrother.pned.gui.control.requests.EdgeRequest;
import de.markusrother.pned.gui.control.requests.EdgeRequestListener;
import de.markusrother.pned.gui.control.requests.LabelRequest;
import de.markusrother.pned.gui.control.requests.LabelRequestListener;
import de.markusrother.pned.gui.control.requests.NodeRequest;
import de.markusrother.pned.gui.control.requests.NodeRequestListener;
import de.markusrother.pned.gui.control.requests.PnRequestTarget;

/**
 * <p>
 * Channel of communication between components as well as to an instance of
 * {@link de.markusrother.pned.control.EventAwarePetriNet}.
 * </p>
 * <p>
 * Changes in the GUI are communicated via an instance of this class to other
 * components, listeners, and other parties interested in state changes. For
 * example, once a selection starts other listeners must be temporarily
 * suspended, and once that selection ends they may be reestablished.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.EventAwarePetriNet
 */
public class PnEventBus extends EventBus
    implements
        PnCommandTarget,
        PnEventTarget,
        PnRequestTarget {

    /** {@inheritDoc} */
    @Override
    public void createPetriNet(final PetriNetEditCommand cmd) {
        for (final PetriNetListener l : getListeners(PetriNetListener.class)) {
            l.createPetriNet(cmd);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
        for (final NodeListener l : getListeners(NodeListener.class)) {
            l.setCurrentNodeType(cmd);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void requestNode(final NodeRequest req) {
        final NodeRequestListener[] listeners = getListeners(NodeRequestListener.class);
        // TODO - To be more performant, we could keep a field reference to a
        // resizable threadpool. Alternatively (better): Create an own
        // ThreadPool and a threadpool factory for each request. That custom
        // threadpool can then cancel threads of the same queue, as soon as the
        // first thread yields a result. What we need is a compromise between a
        // Executors.newSingleThreadExecutor() and the cached or fixed size
        // pools!
        // Also NOTE that the number of threads is limited, depending on
        // hardware settings. If the executor in fact instantiates all those
        // threads without killing them when done, we're in trouble.
        final ExecutorService threadPool = Executors.newCachedThreadPool();
        for (final NodeRequestListener l : listeners) {
            final SwingWorker<?, Object> worker = req.createWorker(l);
            threadPool.submit(worker);
            // NOTE - Do NOT use {@code worker.execute();} The default
            // SwingWorker thread pool is not large enough!
        }
    }

    /** {@inheritDoc} */
    @Override
    public void requestLabel(final LabelRequest req) {
        final LabelRequestListener[] listeners = getListeners(LabelRequestListener.class);
        final ExecutorService threadPool = Executors.newCachedThreadPool();
        for (final LabelRequestListener l : listeners) {
            final SwingWorker<?, Object> worker = req.createWorker(l);
            threadPool.submit(worker);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void requestEdge(final EdgeRequest req) {
        final EdgeRequestListener[] listeners = getListeners(EdgeRequestListener.class);
        final ExecutorService threadPool = Executors.newCachedThreadPool();
        for (final EdgeRequestListener l : listeners) {
            final SwingWorker<?, Object> worker = req.createWorker(l);
            threadPool.submit(worker);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void nodesSelected(final NodeMultiSelectionEvent e) {
        for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
            l.nodesSelected(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void nodesUnselected(final NodeMultiSelectionEvent e) {
        for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
            l.nodesUnselected(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionFinished(final NodeMultiSelectionEvent e) {
        for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
            l.nodeSelectionFinished(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionCancelled(final NodeMultiSelectionEvent e) {
        for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
            l.nodeSelectionCancelled(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void componentEntered(final EdgeEditEvent e) {
        for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
            l.componentEntered(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void componentExited(final EdgeEditEvent e) {
        for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
            l.componentExited(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void edgeCancelled(final EdgeEditEvent e) {
        for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
            l.edgeCancelled(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void edgeFinished(final EdgeEditEvent e) {
        for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
            l.edgeFinished(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void edgeStarted(final EdgeEditEvent e) {
        for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
            l.edgeStarted(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final PlaceLayoutCommand cmd) {
        for (final PlaceLayoutListener l : getListeners(PlaceLayoutListener.class)) {
            switch (cmd.getType()) {
            case SIZE:
                l.setSize(cmd);
                break;
            case SELECTION_COLOR:
            case DEFAULT_COLOR:
            case DEFAULT_BORDER_COLOR:
            case SELECTION_BORDER_COLOR:
            case HOVER_COLOR:
            case HOVER_BORDER_COLOR:
            default:
                throw new IllegalStateException();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final TransitionLayoutCommand cmd) {
        for (final TransitionLayoutListener l : getListeners(TransitionLayoutListener.class)) {
            switch (cmd.getType()) {
            case SIZE:
                l.setSize(cmd);
                break;
            case SELECTION_COLOR:
            case DEFAULT_COLOR:
            case DEFAULT_BORDER_COLOR:
            case SELECTION_BORDER_COLOR:
            case HOVER_COLOR:
            case HOVER_BORDER_COLOR:
            default:
                throw new IllegalStateException();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final MarkingLayoutCommand cmd) {
        for (final MarkingLayoutListener l : getListeners(MarkingLayoutListener.class)) {
            switch (cmd.getType()) {
            case SIZE:
                l.setSize(cmd);
                break;
            case SELECTION_COLOR:
            case DEFAULT_COLOR:
            case DEFAULT_BORDER_COLOR:
            case SELECTION_BORDER_COLOR:
            case HOVER_COLOR:
            case HOVER_BORDER_COLOR:
            default:
                throw new IllegalStateException();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final EdgeLayoutCommand cmd) {
        for (final EdgeLayoutListener l : getListeners(EdgeLayoutListener.class)) {
            switch (cmd.getType()) {
            case SIZE:
                l.setSize(cmd);
                break;
            case SELECTION_COLOR:
            case DEFAULT_COLOR:
            case DEFAULT_BORDER_COLOR:
            case SELECTION_BORDER_COLOR:
            case HOVER_COLOR:
            case HOVER_BORDER_COLOR:
            default:
                throw new IllegalStateException();
            }
        }
    }

}
