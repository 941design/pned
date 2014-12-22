package de.markusrother.pned.core;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.markusrother.pned.core.TransitionActivationEvent.Type;
import de.markusrother.pned.gui.PetriNetCommandSource;
import de.markusrother.pned.gui.events.AbstractNodeCreationCommand;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

/**
 * TODO
 * 
 * <ul>
 * <li>use same id twice</li>
 * <li>multiple edges</li>
 * <li>circular edges</li>
 * <li>circular structure</li>
 * <li>remove non-existing node</li>
 * </ul>
 *
 */
public class EventAwarePetriNetTest
	implements
		TransitionActivationListener {

	private static final String p1 = "place1";
	private static final String p2 = "place2";
	private static final String t1 = "transition1";
	private static final String t2 = "transition2";
	private static final String e1 = "edge1";
	private static final String e2 = "edge2";
	private static final int NO_MARKING = 0;
	private static final Point DEFAULT_ORIGIN = AbstractNodeCreationCommand.defaultNodeOrigin;

	private List<EventObject> events;
	private EventAwarePetriNet net;
	private Object commandSource;

	private class EventBusMock
		implements
			PetriNetCommandSource,
			TransitionActivationListener {

		private final EventListenerList listeners = new EventListenerList();

		public EventBusMock() {
		}

		@Override
		public <T extends EventListener> T[] getListeners(final Class<T> clazz) {
			return listeners.getListeners(clazz);
		}

		@Override
		public <T extends EventListener> void addListener(final Class<T> clazz, final T l) {
			listeners.add(clazz, l);
		}

		@Override
		public <T extends EventListener> void removeListener(final Class<T> clazz, final T l) {
			listeners.remove(clazz, l);
		}

		public int getListenerCount() {
			return listeners.getListenerCount();
		}

		@Override
		public void transitionActivated(final TransitionActivationEvent e) {
			// IGNORE
		}

		@Override
		public void transitionDeactivated(final TransitionActivationEvent e) {
			// IGNORE
		}
	}

	@Before
	public void setUp() {
		this.commandSource = this;
		this.events = new LinkedList<>();
		this.net = new EventAwarePetriNet(new EventBusMock());
		net.addTransitionActivationListener(this);
	}

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		events.add(e);
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		events.add(e);
	}

	private <T extends EventObject> Collection<T> getEvents(final Class<T> clazz) {
		final Collection<T> selectedEvents = new LinkedList<>();
		for (final EventObject event : events) {
			if (clazz.isInstance(event)) {
				final @SuppressWarnings("unchecked") T e = (T) event;
				selectedEvents.add(e);
			}
		}
		return selectedEvents;
	}

	private void createPlace(final String placeId) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(commandSource, placeId);
		net.createPlace(cmd);
	}

	private void createPlace(final String placeId, final int marking) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(commandSource, placeId);
		net.createPlace(cmd);
		setMarking(placeId, marking);
	}

	private void setMarking(final String placeId, final int marking) {
		final PlaceEditEvent cmd = new PlaceEditEvent(commandSource, placeId, marking);
		net.setMarking(cmd);
	}

	private void createTransition(final String transitionId) {
		final TransitionCreationCommand cmd = new TransitionCreationCommand(commandSource, transitionId);
		net.createTransition(cmd);
	}

	private void createEdge(final String edgeId, final String sourceId, final String targetId) {
		final EdgeCreationCommand cmd = new EdgeCreationCommand(commandSource, edgeId, sourceId, targetId);
		net.createEdge(cmd);
	}

	private void removeNode(final String nodeId) {
		final NodeRemovalEvent cmd = new NodeRemovalEvent(commandSource, nodeId);
		net.nodeRemoved(cmd);
	}

	private void assertPlacesContains(final String placeId, final Point origin, final int marking) {
		final PlaceModel place = net.getPlace(placeId);
		Assert.assertNotNull(place);
		assertPlaceEquals(placeId, origin, marking, place);
		assertNodeEquals(placeId, origin, place);
	}

	private void assertPlaceEquals(final String placeId, final Point origin, final int marking, final PlaceModel place) {
		Assert.assertEquals(marking, place.getMarking());
		assertNodeEquals(placeId, origin, place);
	}

	private void assertTransitionsContains(final String transitionId, final Point origin) {
		final TransitionModel transition = net.getTransition(transitionId);
		Assert.assertNotNull(transition);
		assertNodeEquals(transitionId, origin, transition);
	}

	private void assertNodeEquals(final String nodeId, final Point origin, final NodeModel node) {
		Assert.assertEquals(nodeId, node.getId());
		Assert.assertEquals(origin, node.getOrigin());
	}

	private void assertActiveTransitionsContains(final String transitionId, final Point origin) {
		final Collection<TransitionModel> transitions = net.getActiveTransitions();
		for (final TransitionModel transition : transitions) {
			if (transition.hasId(transitionId)) {
				assertNodeEquals(transitionId, origin, transition);
			}
		}
	}

	private void assertPlacesSizeEquals(final int size) {
		assertEquals(size, net.getPlaces().size());
	}

	private void assertTransitionsSizeEquals(final int size) {
		assertEquals(size, net.getTransitions().size());
	}

	private void assertActiveTransitionsSizeEquals(final int size) {
		assertEquals(size, net.getActiveTransitions().size());
	}

	private void assertEdgesSizeEquals(final int size) {
		assertEquals(size, net.getEdges().size());
	}

	private void assertTransitionWasActivated(final String transitionId) {
		for (final TransitionActivationEvent e : getEvents(TransitionActivationEvent.class)) {
			if (Type.ACTIVATION.equals(e.getType()) && transitionId.equals(e.getTransitionId())) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.fail();
	}

	private void assertTransitionWasDeactivated(final String transitionId) {
		for (final TransitionActivationEvent e : getEvents(TransitionActivationEvent.class)) {
			if (Type.DEACTIVATION.equals(e.getType()) && transitionId.equals(e.getTransitionId())) {
				Assert.assertTrue(true);
				return;
			}
		}
		Assert.fail();
	}

	@Test
	public void testAllListenersAdded() {
		final EventBusMock eventSource = new EventBusMock();
		final PetriNetImpl petriNet = new EventAwarePetriNet(eventSource);
		assertEquals(petriNet, eventSource.getListeners(NodeCreationListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(EdgeCreationListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(NodeRemovalListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(NodeMotionListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(PlaceEditListener.class)[0]);
		assertEquals(5, eventSource.getListenerCount());
	}

	@Test
	public void testNewNet() {
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
	}

	@Test
	public void testCreateSinglePlace() {
		createPlace(p1);
		assertPlacesSizeEquals(1);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
		assertPlacesContains(p1, DEFAULT_ORIGIN, NO_MARKING);
	}

	@Test
	public void testCreatePlaceWithoutId() {
		final String id = null;
		createPlace(id);
		assertPlacesSizeEquals(1);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
		// TODO - should this create an id lazily?
		throw new RuntimeException("TODO");
		// assertPlaceMatches("foobar", DEFAULT_ORIGIN, NO_MARKING);
	}

	@Test
	public void testRemovePlace() {
		createPlace(p1);
		removeNode(p1);
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
	}

	@Test
	public void testRemoveTransition() {
		createTransition(t1);
		removeNode(t1);
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
	}

	@Test
	public void testCreateSingleTransition() {
		createTransition(t1);
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(1);
		assertEdgesSizeEquals(0);
		assertTransitionsContains(t1, DEFAULT_ORIGIN);
	}

	@Test
	public void testCreateTransitionWithoutId() {
		final String id = null;
		createTransition(id);
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(1);
		assertEdgesSizeEquals(0);
		// TODO - should this create an id lazily?
		throw new RuntimeException("TODO");
		// assertPlaceMatches("foobar", DEFAULT_ORIGIN, NO_MARKING);
	}

	@Test(expected = NoSuchNodeException.class)
	public void testFailCreatingUnrootedEdge() {
		createEdge(e1, p1, t1);
	}

	@Test
	public void testCreateEdge() {
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		assertPlacesSizeEquals(1);
		assertTransitionsSizeEquals(1);
		assertEdgesSizeEquals(1);
		// TODO
	}

	@Test
	public void testPlaceRemovalRemovesEdge() {
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		removeNode(p1);
		assertPlacesSizeEquals(0);
		assertTransitionsSizeEquals(1);
		assertEdgesSizeEquals(0);
	}

	@Test
	public void testTransitionRemovalRemovesEdge() {
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		removeNode(t1);
		assertPlacesSizeEquals(1);
		assertTransitionsSizeEquals(0);
		assertEdgesSizeEquals(0);
	}

	@Test
	public void testSingleTransitionIsActive() {
		createTransition(t1);
		assertActiveTransitionsSizeEquals(1);
		assertActiveTransitionsContains(t1, DEFAULT_ORIGIN);
	}

	@Test
	public void testConnectedInactiveTransition() {
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		assertActiveTransitionsSizeEquals(0);
	}

	@Test
	public void testConnectedAndActiveTransition() {
		createPlace(p1, 1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		assertActiveTransitionsSizeEquals(1);
	}

	@Test
	public void testTwoIncomingEdgesActivateTransition() {
		createPlace(p1, 42);
		createPlace(p2, 23);
		createTransition(t1);
		createEdge(e1, p1, t1);
		createEdge(e2, p2, t1);
		assertActiveTransitionsSizeEquals(1);
	}

	@Test
	public void testSettingMarkingActivesTransition() {
		// Set up:
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		// Change:
		setMarking(p1, 23);
		// Assert postconditions:
		assertTransitionWasActivated(t1);
	}

	@Test
	public void testChangingMarkingDeactivesTransition() {
		// Set up:
		createPlace(p1, 1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		// Change:
		setMarking(p1, 0);
		// Assert postconditions:
		assertTransitionWasDeactivated(t1);
	}

	@Test
	public void testConnectingEdgeDeactivesTransition() {
		// Set up:
		createPlace(p1, 1);
		createPlace(p2, 0);
		createTransition(t1);
		createEdge(e1, p1, t1);
		// Assert preconditions
		assertActiveTransitionsSizeEquals(1);
		// Change:
		createEdge(e2, p2, t1);
		// Assert postconditions:
		assertActiveTransitionsSizeEquals(0);
		assertTransitionWasDeactivated(t1);
	}

	@Test
	public void testRemovingEdgeActivesTransition() {
		// Set up:
		createPlace(p1, 1);
		createPlace(p2, 0);
		createTransition(t1);
		createEdge(e1, p1, t1);
		createEdge(e2, p2, t1);
		// Assert preconditions
		assertActiveTransitionsSizeEquals(0);
		// Change:
		removeNode(p2);
		// Assert postconditions:
		assertActiveTransitionsSizeEquals(1);
		assertTransitionWasActivated(t1);
	}

	@Test
	public void testRemovingPlaceActivesTransitions() {
		// Set up:
		createPlace(p1, 0);
		createTransition(t1);
		createTransition(t2);
		createEdge(e1, p1, t1);
		createEdge(e2, p1, t2);
		System.out.println(net);
		// Assert preconditions
		assertActiveTransitionsSizeEquals(0);
		// Change:
		removeNode(p1);
		// Assert postconditions:
		assertActiveTransitionsSizeEquals(2);
		assertTransitionWasActivated(t1);
		assertTransitionWasActivated(t2);
	}

	@Test
	public void testMarshalling() throws Exception {
		// Set up:
		createPlace(p1, 0);
		createTransition(t1);
		createTransition(t2);
		createEdge(e1, p1, t1);
		createEdge(e2, p1, t2);

		final JAXBContext jaxbContext = JAXBContext.newInstance(PetriNetImpl.class, PlaceImpl.class,
				TransitionImpl.class, EdgeImpl.class);
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(net, System.out);
	}
}
