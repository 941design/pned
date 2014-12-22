package de.markusrother.pned.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import de.markusrother.pned.core.TransitionActivationEvent.Type;
import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.PetriNetCommandSource;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

public class EventAwarePetriNet extends PetriNetImpl
	implements
		NodeCreationListener,
		EdgeCreationListener,
		NodeRemovalListener,
		NodeMotionListener,
		PlaceEditListener,
		LabelEditListener,
		TransitionActivationEventSource {

	final PetriNetCommandSource commandSource;

	private final Collection<TransitionActivationListener> transitionActivationListeners;

	public <T extends PetriNetCommandSource & TransitionActivationListener> EventAwarePetriNet(final T eventBus) {
		this.commandSource = eventBus;
		this.transitionActivationListeners = new LinkedList<>();

		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceEditListener.class, this);
		// eventBus.addListener(LabelEditListener.class, this); // FIXME - test first!

		this.addTransitionActivationListener(eventBus);
	}

	@Override
	public void addTransitionActivationListener(final TransitionActivationListener l) {
		transitionActivationListeners.add(l);
	}

	@Override
	public void removeTransitionActivationListener(final TransitionActivationListener l) {
		transitionActivationListeners.remove(l);
	}

	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		final String placeId = cmd.getNodeId();
		final Point point = cmd.getPoint();
		if (placeId != null) {
			createPlace(placeId, point);
		} else {
			final PlaceModel place = createPlace(point);
			// FIXME - cmd.fulfillNodeIdPromise(place.getId());
		}
	}

	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		final String transitionId = cmd.getNodeId();
		final Point point = cmd.getPoint();
		if (transitionId != null) {
			createTransition(transitionId, point);
		} else {
			createTransition(point);
		}
	}

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

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE, OBSOLETE
	}

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

	private void fireTransitionDeactivationEvent(final Collection<TransitionModel> deactivated) {
		for (final TransitionModel transition : deactivated) {
			final TransitionActivationEvent e = new TransitionActivationEvent(Type.DEACTIVATION, this,
					transition.getId());
			for (final TransitionActivationListener listener : transitionActivationListeners) {
				listener.transitionDeactivated(e);
			}
		}
	}

	private void fireTransitionActivationEvent(final Collection<TransitionModel> activated) {
		for (final TransitionModel transition : activated) {
			final TransitionActivationEvent e = new TransitionActivationEvent(Type.ACTIVATION, this, transition.getId());
			for (final TransitionActivationListener listener : transitionActivationListeners) {
				listener.transitionActivated(e);
			}
		}
	}

}
