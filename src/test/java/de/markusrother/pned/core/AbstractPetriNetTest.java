package de.markusrother.pned.core;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.control.EventAwarePetriNet;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.events.TransitionActivationEvent.Type;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.core.listeners.TransitionActivationListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;

public abstract class AbstractPetriNetTest
	implements
		TransitionActivationListener {

	protected static final String p1 = "place1";
	protected static final String p2 = "place2";
	protected static final String p3 = "place3";
	protected static final String t1 = "transition1";
	protected static final String t2 = "transition2";
	protected static final String e1 = "edge1";
	protected static final String e2 = "edge2";
	protected static final String e3 = "edge3";
	protected static final String e4 = "edge4";
	protected static final String l1 = "label1";

	protected static final int NO_MARKING = 0;
	protected static final Point DEFAULT_ORIGIN = new Point(100, 100);

	protected List<EventObject> events;
	protected EventAwarePetriNet net;
	private Object source;
	private EventBus eventBus;

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		events.add(e);
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		events.add(e);
	}

	protected <T extends EventObject> Collection<T> getEvents(final Class<T> clazz) {
		final Collection<T> selectedEvents = new LinkedList<>();
		for (final EventObject event : events) {
			if (clazz.isInstance(event)) {
				final @SuppressWarnings("unchecked") T e = (T) event;
				selectedEvents.add(e);
			}
		}
		return selectedEvents;
	}

	@Before
	public void setUp() {
		this.source = this;
		this.events = new LinkedList<>();
		this.eventBus = new EventBus();
		this.net = new EventAwarePetriNet(this.eventBus);
		net.addTransitionActivationListener(this);
	}

	private <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return eventBus.getListeners(clazz);
	}

	protected Collection<PlaceModel> getPlaces() {
		return net.getPlaces();
	}

	protected Collection<TransitionModel> getTransitions() {
		return net.getTransitions();
	}

	protected void createPlace(final String placeId) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(source, placeId, DEFAULT_ORIGIN);
		createPlace(cmd);
	}

	protected void createPlace(final String placeId, final String label) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(source, placeId, DEFAULT_ORIGIN);
		createPlace(cmd);
		setLabel(placeId, label);
	}

	protected void createPlace(final String placeId, final int marking) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(source, placeId, DEFAULT_ORIGIN);
		createPlace(cmd);
		setMarking(placeId, marking);
	}

	private void createPlace(final PlaceCreationCommand cmd) {
		for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
			l.createPlace(cmd);
		}
	}

	protected void setLabel(final String placeId, final String label) {
		final LabelEditCommand cmd = new LabelEditCommand(source, LabelEditCommand.Type.SET_LABEL, placeId, label);
		for (final LabelEditListener l : getListeners(LabelEditListener.class)) {
			l.setLabel(cmd);
		}
	}

	protected void setMarking(final String placeId, final int marking) {
		final PlaceEditCommand cmd = new PlaceEditCommand(source, PlaceEditCommand.Type.SET_MARKING, placeId, marking);
		for (final PlaceEditListener l : getListeners(PlaceEditListener.class)) {
			l.setMarking(cmd);
		}
	}

	protected void createTransition(final String transitionId) {
		final TransitionCreationCommand cmd = new TransitionCreationCommand(source, transitionId, DEFAULT_ORIGIN);
		createTransition(cmd);
	}

	private void createTransition(final TransitionCreationCommand cmd) {
		for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
			l.createTransition(cmd);
		}
	}

	protected void createEdge(final String edgeId, final String sourceId, final String targetId) {
		final EdgeCreationCommand cmd = new EdgeCreationCommand(source, edgeId, sourceId, targetId);
		for (final EdgeCreationListener l : getListeners(EdgeCreationListener.class)) {
			l.createEdge(cmd);
		}
	}

	protected void removeNode(final String nodeId) {
		final NodeRemovalCommand cmd = new NodeRemovalCommand(source, nodeId);
		for (final NodeRemovalListener l : getListeners(NodeRemovalListener.class)) {
			l.nodeRemoved(cmd);
		}
	}

	protected void assertPlacesContains(final String placeId, final Point origin, final int marking) {
		final PlaceModel place = net.getPlace(placeId);
		Assert.assertNotNull(place);
		assertPlaceEquals(placeId, origin, marking, place);
		assertNodeEquals(placeId, origin, place);
	}

	private void assertPlaceEquals(final String placeId, final Point origin, final int marking, final PlaceModel place) {
		Assert.assertEquals(marking, place.getMarking());
		assertNodeEquals(placeId, origin, place);
	}

	protected void assertTransitionsContains(final String transitionId, final Point origin) {
		final TransitionModel transition = net.getTransition(transitionId);
		Assert.assertNotNull(transition);
		assertNodeEquals(transitionId, origin, transition);
	}

	private void assertNodeEquals(final String nodeId, final Point origin, final NodeModel node) {
		Assert.assertEquals(nodeId, node.getId());
		Assert.assertEquals(origin, node.getPosition());
	}

	protected void assertActiveTransitionsContains(final String transitionId, final Point origin) {
		final Collection<TransitionModel> transitions = net.getActiveTransitions();
		for (final TransitionModel transition : transitions) {
			if (transition.hasId(transitionId)) {
				assertNodeEquals(transitionId, origin, transition);
			}
		}
	}

	protected void assertPlacesSizeEquals(final int size) {
		assertEquals(size, net.getPlaces().size());
	}

	protected void assertTransitionsSizeEquals(final int size) {
		assertEquals(size, net.getTransitions().size());
	}

	protected void assertActiveTransitionsSizeEquals(final int size) {
		assertEquals(size, net.getActiveTransitions().size());
	}

	protected void assertEdgesSizeEquals(final int size) {
		assertEquals(size, net.getEdges().size());
	}

	protected void assertTransitionWasActivated(final String transitionId) {
		for (final TransitionActivationEvent e : getEvents(TransitionActivationEvent.class)) {
			if (Type.ACTIVATION.equals(e.getType()) && transitionId.equals(e.getTransitionId())) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.fail();
	}

	protected void assertTransitionWasDeactivated(final String transitionId) {
		for (final TransitionActivationEvent e : getEvents(TransitionActivationEvent.class)) {
			if (Type.DEACTIVATION.equals(e.getType()) && transitionId.equals(e.getTransitionId())) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.fail();
	}

}
