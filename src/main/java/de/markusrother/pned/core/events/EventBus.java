package de.markusrother.pned.core.events;

import java.io.IOException;
import java.util.EventListener;

import javax.swing.SwingWorker;
import javax.swing.event.EventListenerList;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.LabelEditEvent;
import de.markusrother.pned.core.commands.NodeMovedEvent;
import de.markusrother.pned.core.commands.NodeRemovalEvent;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditEvent;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.IdRequestListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.NodeRemovalListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.core.listeners.TransitionActivationListener;
import de.markusrother.pned.core.requests.IdRequest;

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
		PetriNetCommandSource,
		EventTarget,
		RequestTarget,
		NodeRemovalListener {

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

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMovedEvent e) {
		for (final NodeMotionListener l : getListeners(NodeMotionListener.class)) {
			l.nodeMoved(e);
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
