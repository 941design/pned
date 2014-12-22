package de.markusrother.pned.core;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Collection;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;

import de.markusrother.pned.core.TransitionActivationEvent.Type;
import de.markusrother.pned.gui.events.AbstractNodeCreationCommand;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

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
	protected static final Point DEFAULT_ORIGIN = AbstractNodeCreationCommand.defaultNodeOrigin;

	protected List<EventObject> events;
	protected EventAwarePetriNet net;
	private Object commandSource;

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
		this.commandSource = this;
		this.events = new LinkedList<>();
		this.net = new EventAwarePetriNet(new CommandSourceMock());
		net.addTransitionActivationListener(this);
	}

	protected void createPlace(final String placeId) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(commandSource, placeId);
		net.createPlace(cmd);
	}

	protected void createPlace(final String placeId, final String label) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(commandSource, placeId);
		net.createPlace(cmd);
		setLabel(placeId, label);
	}

	protected void setLabel(final String placeId, final String label) {
		final LabelEditEvent cmd = new LabelEditEvent(commandSource, placeId, label);
		net.setLabel(cmd);
	}

	protected void createPlace(final String placeId, final int marking) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(commandSource, placeId);
		net.createPlace(cmd);
		setMarking(placeId, marking);
	}

	protected void setMarking(final String placeId, final int marking) {
		final PlaceEditEvent cmd = new PlaceEditEvent(commandSource, placeId, marking);
		net.setMarking(cmd);
	}

	protected void createTransition(final String transitionId) {
		final TransitionCreationCommand cmd = new TransitionCreationCommand(commandSource, transitionId);
		net.createTransition(cmd);
	}

	protected void createEdge(final String edgeId, final String sourceId, final String targetId) {
		final EdgeCreationCommand cmd = new EdgeCreationCommand(commandSource, edgeId, sourceId, targetId);
		net.createEdge(cmd);
	}

	protected void removeNode(final String nodeId) {
		final NodeRemovalEvent cmd = new NodeRemovalEvent(commandSource, nodeId);
		net.nodeRemoved(cmd);
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
