package de.markusrother.pned.control;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.event.EventListenerList;
import javax.xml.bind.JAXBException;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.events.PlaceChangeEvent;
import de.markusrother.pned.control.events.PlaceEventObject;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.events.TransitionActivationEvent.Type;
import de.markusrother.pned.control.listeners.CommandTarget;
import de.markusrother.pned.control.listeners.EdgeCreationListener;
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
import de.markusrother.pned.core.DefaultPetriNet;
import de.markusrother.pned.core.NoSuchNodeException;
import de.markusrother.pned.core.TransitionInactiveException;
import de.markusrother.pned.core.UnavailableIdException;
import de.markusrother.pned.core.model.EdgeModel;
import de.markusrother.pned.core.model.NodeModel;
import de.markusrother.pned.core.model.PlaceModel;
import de.markusrother.pned.core.model.TransitionModel;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
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
		RequestTarget {

	private final EventBus eventBus;
	private final EventListenerList listeners;

	/**
	 * <p>
	 * create.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @return a {@link de.markusrother.pned.control.EventAwarePetriNet}
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
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public EventAwarePetriNet(final EventBus eventBus) {
		this.eventBus = eventBus;
		this.listeners = new EventListenerList();

		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.addListener(TransitionListener.class, this);

		this.addTransitionActivationListener(eventBus);
	}

	/**
	 * <p>
	 * addTransitionActivationListener.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.control.listeners.TransitionActivationListener}
	 *            object.
	 */
	public void addTransitionActivationListener(final TransitionActivationListener l) {
		listeners.add(TransitionActivationListener.class, l);
	}

	/**
	 * <p>
	 * removeTransitionActivationListener.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.control.listeners.TransitionActivationListener}
	 *            object.
	 */
	public void removeTransitionActivationListener(final TransitionActivationListener l) {
		listeners.remove(TransitionActivationListener.class, l);
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
		// IGNORE - We could import on top of this net...
		// It is however not appropriate to start a new net here, as
		// each EventBus is bound to a net.
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
			final TransitionModel transition = createTransition(nodeId, point);
			final TransitionActivationEvent evt = new TransitionActivationEvent(this, //
					Type.ACTIVATION, //
					transition.getId());
			fireTransitionAcivationEvents(Arrays.asList(evt));
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

		maybeFireTransitionActivationEvents(new Runnable() {
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
	public void removeEdge(final EdgeRemoveCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final EdgeModel edge = getEdge(edgeId);
		if (edge == null) {
			// TODO
			throw new RuntimeException("TODO");
		}
		maybeFireTransitionActivationEvents(new Runnable() {
			@Override
			public void run() {
				removeEdge(edge);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		for (final NodeModel node : getNodes()) {
			final String nodeId = node.getId();
			if (e.getNodeId().contains(nodeId)) {
				final int deltaX = e.getDeltaX();
				final int deltaY = e.getDeltaY();
				node.move(deltaX, deltaY);
				return;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		final String nodeId = e.getNodeId();
		final PlaceModel place = getPlace(nodeId);
		if (place != null) {
			maybeFireTransitionActivationEvents(new Runnable() {
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
	public void setMarking(final PlaceEventObject e) {
		if (e.getSource() == this) {
			return;
		}
		final String placeId = e.getPlaceId();
		final int marking = e.getMarking();
		maybeFireTransitionActivationEvents(new Runnable() {
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
	public void setLabel(final LabelEditCommand e) {
		final String placeId = e.getElementId();
		final String label = e.getLabel();
		maybeFireTransitionActivationEvents(new Runnable() {
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
	private void maybeFireTransitionActivationEvents(final Runnable runnable) {
		final Collection<TransitionActivationEvent> events = maybeGetTransitionActivationEvents(runnable);
		fireTransitionAcivationEvents(events);
	}

	/**
	 * <p>
	 * maybeGetTransitionActivationEvents.
	 * </p>
	 *
	 * @param runnable
	 *            a {@link java.lang.Runnable} object.
	 * @return a {@link java.util.Collection} object.
	 */
	private Collection<TransitionActivationEvent> maybeGetTransitionActivationEvents(final Runnable runnable) {

		final Collection<TransitionActivationEvent> events = new LinkedList<>();

		final Collection<TransitionModel> activeBefore = getActiveTransitions();

		runnable.run();

		final Collection<TransitionModel> activeAfter = getActiveTransitions();

		final Collection<TransitionModel> deactivated = new ArrayList<>(activeBefore);
		deactivated.removeAll(activeAfter);
		events.addAll(createTransitionDeactivationEvents(deactivated));

		final Collection<TransitionModel> activated = new ArrayList<>(activeAfter);
		activated.removeAll(activeBefore);
		events.addAll(createTransitionActivationEvent(activated));

		return events;
	}

	/**
	 * <p>
	 * fireTransitionAcivationEvents.
	 * </p>
	 *
	 * @param events
	 *            a {@link java.util.Collection} object.
	 */
	private void fireTransitionAcivationEvents(final Collection<TransitionActivationEvent> events) {
		for (final TransitionActivationEvent evt : events) {
			for (final TransitionActivationListener listener : listeners
					.getListeners(TransitionActivationListener.class)) {
				switch (evt.getType()) {
				case ACTIVATION:
					listener.transitionActivated(evt);
					break;
				case DEACTIVATION:
					listener.transitionDeactivated(evt);
					break;
				default:
					throw new IllegalStateException();
				}
			}
		}
	}

	/**
	 * <p>
	 * createTransitionDeactivationEvents.
	 * </p>
	 *
	 * @param deactivated
	 *            a {@link java.util.Collection} object.
	 * @return a {@link java.util.Collection} object.
	 */
	private Collection<TransitionActivationEvent> createTransitionDeactivationEvents(
			final Collection<TransitionModel> deactivated) {
		final Collection<TransitionActivationEvent> events = new LinkedList<>();
		for (final TransitionModel transition : deactivated) {
			final TransitionActivationEvent e = new TransitionActivationEvent(this, //
					Type.DEACTIVATION, //
					transition.getId());
			events.add(e);
		}
		return events;
	}

	/**
	 * <p>
	 * createTransitionActivationEvent.
	 * </p>
	 *
	 * @param activated
	 *            a {@link java.util.Collection} object.
	 * @return a {@link java.util.Collection} object.
	 */
	private Collection<TransitionActivationEvent> createTransitionActivationEvent(
			final Collection<TransitionModel> activated) {
		final Collection<TransitionActivationEvent> events = new LinkedList<>();
		for (final TransitionModel transition : activated) {
			final TransitionActivationEvent evt = new TransitionActivationEvent(this, //
					Type.ACTIVATION, //
					transition.getId());
			events.add(evt);
		}
		return events;
	}

	/** {@inheritDoc} */
	@Override
	public void fireTransition(final TransitionExecutionCommand cmd) {
		final String transitionId = cmd.getTransitionId();
		try {

			final Collection<TransitionActivationEvent> events = new LinkedList<>();

			final Collection<TransitionModel> activeBefore = getActiveTransitions();

			final TransitionModel transition = getTransition(transitionId);
			fireTransition(transition);

			final Collection<TransitionModel> activeAfter = getActiveTransitions();

			final Collection<TransitionModel> deactivated = new ArrayList<>(activeBefore);
			deactivated.removeAll(activeAfter);
			events.addAll(createTransitionDeactivationEvents(deactivated));

			final Collection<TransitionModel> activated = new ArrayList<>(activeAfter);
			activated.removeAll(activeBefore);
			events.addAll(createTransitionActivationEvent(activated));

			fireTransitionAcivationEvents(events);

		} catch (final TransitionInactiveException e) {
			// FIXME - throw some generic exception
			throw new RuntimeException("TODO - Trying to fire inactive transition");
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void decrementInputPlaceMarking(final PlaceModel place) {
		super.decrementInputPlaceMarking(place);
		final PlaceChangeEvent evt = new PlaceChangeEvent(this, //
				PlaceEventObject.Type.SET_MARKING, //
				place.getId(), //
				place.getMarking()); // The new marking
		eventBus.setMarking(evt);
	}

	/** {@inheritDoc} */
	@Override
	protected void incrementOutputPlaceMarking(final PlaceModel place) {
		super.incrementOutputPlaceMarking(place);
		final PlaceChangeEvent evt = new PlaceChangeEvent(this, //
				PlaceEventObject.Type.SET_MARKING, //
				place.getId(), //
				place.getMarking()); // The new marking
		eventBus.setMarking(evt);
	}

}
