package de.markusrother.pned.gui.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.layout.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;
import de.markusrother.pned.gui.listeners.GuiEventTarget;
import de.markusrother.pned.gui.listeners.GuiRequestTarget;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.requests.NodeRequest;

/**
 * FIXME - Create copy constructor from EventBus
 *
 * @author Markus Rother
 * @version 1.0
 */
public class GuiEventBus extends EventBus
	implements
		GuiCommandTarget,
		GuiEventTarget,
		GuiRequestTarget {

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
		// final ExecutorService threadPool =
		// Executors.newFixedThreadPool(listeners.length);
		// FIXME - use one threadpool as field!
		// TODO - Create an own ThreadPool and a threadpool factory for each
		// request. The Threadpool could then cancel threads of the same queue,
		// if the first yields the searched node!
		// FIXME
		final ExecutorService threadPool = Executors.newCachedThreadPool();
		for (final NodeRequestListener l : listeners) {
			final SwingWorker<AbstractNode, Object> worker = req.createWorker(l);
			threadPool.submit(worker);
			// NOTE - Do NOT use {@code worker.execute();} The default
			// SwingWorker thread pool is not large enough!
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
	public void edgeMoved(final EdgeEditEvent e) {
		for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
			l.edgeMoved(e);
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
