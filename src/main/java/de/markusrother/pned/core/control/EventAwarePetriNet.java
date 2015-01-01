package de.markusrother.pned.core.control;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import de.markusrother.pned.core.DefaultPetriNet;
import de.markusrother.pned.core.NodeModel;
import de.markusrother.pned.core.PlaceModel;
import de.markusrother.pned.core.TransitionModel;
import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.LabelEditEvent;
import de.markusrother.pned.core.commands.NodeMovedEvent;
import de.markusrother.pned.core.commands.NodeRemovalEvent;
import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditEvent;
import de.markusrother.pned.core.commands.RemoveSelectedNodesEvent;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.events.TransitionActivationEvent.Type;
import de.markusrother.pned.core.exceptions.NoSuchNodeException;
import de.markusrother.pned.core.exceptions.UnavailableIdException;
import de.markusrother.pned.core.listeners.CommandTarget;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.IdRequestListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.NodeRemovalListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.core.listeners.RequestTarget;
import de.markusrother.pned.core.listeners.TransitionActivationListener;
import de.markusrother.pned.core.requests.IdRequest;
import de.markusrother.pned.io.PetriNetMarshaller;

/**
 * <p>
 * EventAwarePetriNet class.
 * </p>
 *
 * FIXME - should not reside in core unless events are not in gui package.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EventAwarePetriNet extends DefaultPetriNet
	implements
		CommandTarget,
		RequestTarget,
		TransitionActivationEventSource {

	private final Collection<TransitionActivationListener> transitionActivationListeners;

	/**
	 * <p>
	 * create.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @return a {@link de.markusrother.pned.core.control.EventAwarePetriNet}
	 *         object.
	 */
	public static EventAwarePetriNet create(final EventBus eventBus) {
		return new EventAwarePetriNet(eventBus);
	}

	/**
	 * <p>
	 * Constructor for EventAwarePetriNet.
	 * </p>
	 *
	 * @param eventBus
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 */
	public <T extends PetriNetCommandSource & TransitionActivationListener> EventAwarePetriNet(final T eventBus) {
		this.transitionActivationListeners = new LinkedList<>();

		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceEditListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);

		this.addTransitionActivationListener(eventBus);
	}

	/** {@inheritDoc} */
	@Override
	public void addTransitionActivationListener(final TransitionActivationListener l) {
		transitionActivationListeners.add(l);
	}

	/** {@inheritDoc} */
	@Override
	public void removeTransitionActivationListener(final TransitionActivationListener l) {
		transitionActivationListeners.remove(l);
	}

	/** {@inheritDoc} */
	@Override
	public void requestId(final IdRequest req) {
		final String id = createId();
		req.set(id);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) throws IOException {
		final File file = cmd.getFile();
		try (final FileOutputStream out = new FileOutputStream(file)) {
			PetriNetMarshaller.writeXml(this, out);
		} catch (final JAXBException e) {
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		final Point point = cmd.getPoint();
		final String nodeId = cmd.getNodeId();
		try {
			createPlace(nodeId, point);
		} catch (final UnavailableIdException e) {
			// FIXME - create RequestException
			throw new RuntimeException("TODO");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		final Point point = cmd.getPoint();
		final String nodeId = cmd.getNodeId();
		try {
			createTransition(nodeId, point);
		} catch (final UnavailableIdException e) {
			// FIXME - create RequestException
			throw new RuntimeException("TODO");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final String sourceId = cmd.getSourceId();
		final String targetId = cmd.getTargetId();

		maybeFireTransitionActivationEvent(new Runnable() {
			@Override
			public void run() {
				try {
					if (edgeId != null) {
						createEdge(edgeId, sourceId, targetId);
					} else {
						createEdge(sourceId, targetId);
					}
				} catch (final NoSuchNodeException | UnavailableIdException e) {
					// FIXME - throw some generic exception
					throw new RuntimeException("TODO");
				}
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMovedEvent e) {
		for (final NodeModel node : getNodes()) {
			final String nodeId = node.getId();
			if (e.getNodeIds().contains(nodeId)) {
				final int deltaX = e.getDeltaX();
				final int deltaY = e.getDeltaY();
				node.move(deltaX, deltaY);
				return;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		final String nodeId = e.getNodeId();
		final PlaceModel place = getPlace(nodeId);
		if (place != null) {
			maybeFireTransitionActivationEvent(new Runnable() {
				@Override
				public void run() {
					removePlace(place);
				}
			});
			return;
		}
		final TransitionModel transition = getTransition(nodeId);
		if (transition != null) {
			removeTransition(transition);
			return;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE, OBSOLETE
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEditEvent e) {
		final String placeId = e.getPlaceId();
		final int marking = e.getMarking();
		maybeFireTransitionActivationEvent(new Runnable() {
			@Override
			public void run() {
				try {
					setMarking(placeId, marking);
				} catch (final NoSuchNodeException e) {
					// FIXME - throw some generic exception
					throw new RuntimeException("TODO");
				}
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditEvent e) {
		final String placeId = e.getElementId();
		final String label = e.getLabel();
		maybeFireTransitionActivationEvent(new Runnable() {
			@Override
			public void run() {
				try {
					setLabel(placeId, label);
				} catch (final NoSuchNodeException e) {
					// FIXME - throw some generic exception
					throw new RuntimeException("TODO");
				}
			}
		});

	}

	/**
	 * <p>
	 * maybeFireTransitionActivationEvent.
	 * </p>
	 *
	 * @param runnable
	 *            a {@link java.lang.Runnable} object.
	 */
	private void maybeFireTransitionActivationEvent(final Runnable runnable) {
		final Collection<TransitionModel> activeBefore = getActiveTransitions();

		runnable.run();

		final Collection<TransitionModel> activeAfter = getActiveTransitions();

		final Collection<TransitionModel> deactivated = new ArrayList<>(activeBefore);
		deactivated.removeAll(activeAfter);
		fireTransitionDeactivationEvent(deactivated);

		final Collection<TransitionModel> activated = new ArrayList<>(activeAfter);
		activated.removeAll(activeBefore);
		fireTransitionActivationEvent(activated);
	}

	/**
	 * <p>
	 * fireTransitionDeactivationEvent.
	 * </p>
	 *
	 * @param deactivated
	 *            a {@link java.util.Collection} object.
	 */
	private void fireTransitionDeactivationEvent(final Collection<TransitionModel> deactivated) {
		for (final TransitionModel transition : deactivated) {
			final TransitionActivationEvent e = new TransitionActivationEvent(Type.DEACTIVATION, this,
					transition.getId());
			for (final TransitionActivationListener listener : transitionActivationListeners) {
				listener.transitionDeactivated(e);
			}
		}
	}

	/**
	 * <p>
	 * fireTransitionActivationEvent.
	 * </p>
	 *
	 * @param activated
	 *            a {@link java.util.Collection} object.
	 */
	private void fireTransitionActivationEvent(final Collection<TransitionModel> activated) {
		for (final TransitionModel transition : activated) {
			final TransitionActivationEvent e = new TransitionActivationEvent(Type.ACTIVATION, this, transition.getId());
			for (final TransitionActivationListener listener : transitionActivationListeners) {
				listener.transitionActivated(e);
			}
		}
	}

}
