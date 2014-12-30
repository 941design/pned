package de.markusrother.pned.core;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.commands.listeners.TransitionActivationListener;
import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.events.TransitionActivationEvent;
import de.markusrother.pned.events.TransitionActivationEvent.Type;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.PetriNetCommandSource;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.IdRequest;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.IdRequestListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;
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
public class EventAwarePetriNet extends PetriNetImpl
	implements
		PetriNetIOListener,
		IdRequestListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeRemovalListener,
		NodeMotionListener,
		PlaceEditListener,
		LabelEditListener,
		TransitionActivationEventSource {

	final PetriNetCommandSource commandSource;

	private final Collection<TransitionActivationListener> transitionActivationListeners;

	/**
	 * <p>
	 * create.
	 * </p>
	 *
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.gui.EventBus} object.
	 * @return a {@link de.markusrother.pned.core.EventAwarePetriNet} object.
	 */
	public static EventAwarePetriNet create(final EventBus eventMulticaster) {
		return new EventAwarePetriNet(eventMulticaster);
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
	<T extends PetriNetCommandSource & TransitionActivationListener> EventAwarePetriNet(final T eventBus) {
		this.commandSource = eventBus;
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
		createPlace(nodeId, point);
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		final Point point = cmd.getPoint();
		final String nodeId = cmd.getNodeId();
		createTransition(nodeId, point);
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
				if (edgeId != null) {
					createEdge(edgeId, sourceId, targetId);
				} else {
					createEdge(sourceId, targetId);
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
				setMarking(placeId, marking);
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
				setLabel(placeId, label);
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
