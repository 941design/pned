package de.markusrother.pned.gui;

import java.io.IOException;
import java.util.EventListener;

import javax.swing.SwingWorker;
import javax.swing.event.EventListenerList;

import de.markusrother.pned.commands.EdgeLayoutCommand;
import de.markusrother.pned.commands.MarkingLayoutCommand;
import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.commands.PlaceLayoutCommand;
import de.markusrother.pned.commands.TransitionLayoutCommand;
import de.markusrother.pned.commands.listeners.EdgeLayoutListener;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;
import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.commands.listeners.TransitionActivationListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;
import de.markusrother.pned.core.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.IdRequest;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeRequest;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.IdRequestListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

/**
 * TODO - We could distinguish Two sources GUI and MODEL. GUI could be anything
 * static that can be compared against, to distinguish where a creation event
 * came from.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EventBus
	implements
		PetriNetListener,
		PetriNetCommandSource,
		PetriNetIOListener,
		IdRequestListener,
		TransitionActivationListener,
		EventTarget,
		GuiEventTarget,
		NodeListener,
		NodeRequestListener,
		NodeMotionListener,
		NodeCreationListener,
		NodeRemovalListener,
		PlaceEditListener,
		PlaceLayoutListener,
		TransitionLayoutListener,
		MarkingLayoutListener,
		LabelEditListener,
		EdgeLayoutListener {

	private final EventListenerList listeners = new EventListenerList();

	/** {@inheritDoc} */
	@Override
	public <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return listeners.getListeners(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public <T extends EventListener> void addListener(final Class<T> clazz, final T l) {
		// TODO - maintain a map which only dispatches events to given listener?
		// (Would be unconventional)
		listeners.add(clazz, l);
	}

	/** {@inheritDoc} */
	@Override
	public <T extends EventListener> void removeListener(final Class<T> clazz, final T l) {
		listeners.remove(clazz, l);
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		for (final PetriNetListener l : getListeners(PetriNetListener.class)) {
			l.createPetriNet(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		for (final PetriNetIOListener l : getListeners(PetriNetIOListener.class)) {
			l.setCurrentDirectory(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) throws IOException {
		for (final PetriNetIOListener l : getListeners(PetriNetIOListener.class)) {
			l.importPnml(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) throws IOException {
		for (final PetriNetIOListener l : getListeners(PetriNetIOListener.class)) {
			l.exportPnml(cmd);
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
	public void createPlace(final PlaceCreationCommand cmd) {
		for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
			l.createPlace(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
			l.createTransition(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		for (final EdgeCreationListener l : getListeners(EdgeCreationListener.class)) {
			l.createEdge(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEditEvent e) {
		for (final PlaceEditListener l : getListeners(PlaceEditListener.class)) {
			l.setMarking(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		for (final NodeRemovalListener l : getListeners(NodeRemovalListener.class)) {
			l.nodeRemoved(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		for (final NodeRemovalListener l : getListeners(NodeRemovalListener.class)) {
			l.removeSelectedNodes(e);
		}
	}

	/**
	 * <p>
	 * fireNodeSelectionEvent.
	 * </p>
	 *
	 * @param e
	 *            a {@link de.markusrother.pned.gui.events.NodeSelectionEvent}
	 *            object.
	 */
	public void fireNodeSelectionEvent(final NodeSelectionEvent e) {
		for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
			switch (e.getType()) {
			case SELECT:
				l.nodesSelected(e);
				break;
			case DESELECT:
				l.nodesUnselected(e);
				break;
			case FINISH:
				l.nodeSelectionFinished(e);
				break;
			case CANCEL:
				l.nodeSelectionCancelled(e);
				break;
			default:
				throw new IllegalStateException();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMovedEvent e) {
		for (final NodeMotionListener l : getListeners(NodeMotionListener.class)) {
			l.nodeMoved(e);
		}
	}

	/**
	 * <p>
	 * fireEdgeEditEvent.
	 * </p>
	 *
	 * @param e
	 *            a {@link de.markusrother.pned.gui.events.EdgeEditEvent}
	 *            object.
	 */
	public void fireEdgeEditEvent(final EdgeEditEvent e) {
		for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
			switch (e.getType()) {
			case COMPONENT_ENTERED:
				l.targetComponentEntered(e);
				break;
			case COMPONENT_EXITED:
				l.targetComponentExited(e);
				break;
			case EDGE_CHANGED:
				l.edgeMoved(e);
				break;
			case EDGE_CANCELLED:
				l.edgeCancelled(e);
				break;
			case EDGE_FINISHED:
				l.edgeFinished(e);
				break;
			case EDGE_STARTED:
				l.edgeStarted(e);
				break;
			default:
				throw new IllegalStateException();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditEvent e) {
		for (final LabelEditListener l : getListeners(LabelEditListener.class)) {
			l.setLabel(e);
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

	/** {@inheritDoc} */
	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		for (final TransitionActivationListener l : getListeners(TransitionActivationListener.class)) {
			l.transitionActivated(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		for (final TransitionActivationListener l : getListeners(TransitionActivationListener.class)) {
			l.transitionDeactivated(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void requestNode(final NodeRequest req) {
		for (final NodeRequestListener l : getListeners(NodeRequestListener.class)) {
			final SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() {
					l.requestNode(req);
					return null;
				}
			};
			worker.execute();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void requestId(final IdRequest req) {
		for (final IdRequestListener l : getListeners(IdRequestListener.class)) {
			final SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() {
					l.requestId(req);
					return null;
				}
			};
			worker.execute();
		}
	}

}
