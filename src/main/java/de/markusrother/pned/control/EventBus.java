package de.markusrother.pned.control;

import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;
import javax.swing.event.EventListenerList;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.events.PlaceEventObject;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.listeners.CommandTarget;
import de.markusrother.pned.control.listeners.EdgeCreationListener;
import de.markusrother.pned.control.listeners.EventTarget;
import de.markusrother.pned.control.listeners.IdRequestListener;
import de.markusrother.pned.control.listeners.LabelEditListener;
import de.markusrother.pned.control.listeners.NodeCreationListener;
import de.markusrother.pned.control.listeners.NodeMotionListener;
import de.markusrother.pned.control.listeners.PetriNetIOListener;
import de.markusrother.pned.control.listeners.PlaceListener;
import de.markusrother.pned.control.listeners.RequestTarget;
import de.markusrother.pned.control.listeners.TransitionActivationListener;
import de.markusrother.pned.control.listeners.TransitionListener;
import de.markusrother.pned.control.requests.IdRequest;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.util.EventSource;

/**
 * Channel of communication between <b>a single<b> {@link EventAwarePetriNet}
 * and other interested parties.
 * 
 * The following example illustrates event forwarding to the Petri net:
 * 
 * <pre>
 * EventBus eventBus = new EventBus();
 * PetriNetModel net = new EventAwarePetriNet(eventBus);
 * eventBus.createPlace(...);
 * eventBus.createPlace(...);
 * eventBus.createPlace(...);
 * eventBus.createEdge(...);
 * </pre>
 * 
 * The following example illustrates event tapping to a logger:
 * 
 * <pre>
 * EventBus eventBus = new EventBus();
 * PetriNetModel net = new EventAwarePetriNet(eventBus);
 * PetriNetEventLogger logger = new PetriNetEventLogger(eventBus);
 * ...
 * </pre>
 * 
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EventBus
	implements
		EventSource,
		CommandTarget,
		EventTarget,
		RequestTarget {

	/** Collection of all listeners. */
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
	public void removeEdge(final EdgeRemoveCommand cmd) {
		for (final EdgeCreationListener l : getListeners(EdgeCreationListener.class)) {
			l.removeEdge(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEventObject evt) {
		for (final PlaceListener l : getListeners(PlaceListener.class)) {
			l.setMarking(evt);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
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
	public void nodeMoved(final NodeMotionCommand e) {
		for (final NodeMotionListener l : getListeners(NodeMotionListener.class)) {
			l.nodeMoved(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand e) {
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
	public void fireTransition(final TransitionExecutionCommand cmd) {
		for (final TransitionListener l : getListeners(TransitionListener.class)) {
			l.fireTransition(cmd);
		}
	}

	/**
	 * <p>
	 * Convenience method. Posts
	 * {@link de.markusrother.pned.control.requests.IdRequest} and returns
	 * requested id.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String requestId() {
		try {
			final IdRequest req = new IdRequest(this);
			requestId(req);
			return req.get(); // TODO - insert timeout here!
		} catch (final TimeoutException e) {
			// TODO - create custom Exception
			throw new RuntimeException("TODO");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void requestId(final IdRequest req) {
		for (final IdRequestListener l : getListeners(IdRequestListener.class)) {
			final SwingWorker<String, Object> worker = req.createWorker(l);
			worker.execute();
		}
	}

}
