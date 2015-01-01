package de.markusrother.pned.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.markusrother.pned.core.control.EventAwarePetriNet;
import de.markusrother.pned.core.exceptions.NoSuchNodeException;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.IdRequestListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.NodeRemovalListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;

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
public class EventAwarePetriNetTest extends AbstractPetriNetTest {

	@Test
	public void testAllListenersAdded() {
		final CommandSourceMock eventSource = new CommandSourceMock();
		final DefaultPetriNet petriNet = new EventAwarePetriNet(eventSource);
		assertEquals(petriNet, eventSource.getListeners(IdRequestListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(PetriNetIOListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(NodeCreationListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(EdgeCreationListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(NodeRemovalListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(NodeMotionListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(PlaceEditListener.class)[0]);
		assertEquals(petriNet, eventSource.getListeners(LabelEditListener.class)[0]);
		assertEquals(8, eventSource.getListenerCount());
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

}
