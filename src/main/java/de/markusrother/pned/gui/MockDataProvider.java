package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

public class MockDataProvider
	implements
		NodeListener,
		NodeCreationListener,
		NodeRemovalListener,
		NodeSelectionListener,
		EdgeEditListener {

	private int nodeId = 1;

	public static void instantiate(final EventBus eventBus) {
		final MockDataProvider mock = new MockDataProvider();
		eventBus.addListener(NodeListener.class, mock);
		eventBus.addListener(NodeRemovalListener.class, mock);
		eventBus.addListener(NodeCreationListener.class, mock);
		eventBus.addListener(NodeSelectionListener.class, mock);
		eventBus.addListener(EdgeEditListener.class, mock);
	}

	private void log(final ActionEvent event) {
		System.out.println(//
				event.getClass().getSimpleName() //
						+ "\n\tfrom " //
						+ event.getSource().getClass().getSimpleName() //
						// + "\n" //
						// + event //
						+ "\n");
	}

	private MockDataProvider() {
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		log(cmd);
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		log(e);
		e.fulfillNodeIdPromise(String.valueOf(nodeId++));
	}

	@Override
	public void createPlace(final PlaceCreationCommand e) {
		log(e);
	}

	@Override
	public void createTransition(final TransitionCreationCommand e) {
		log(e);
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		log(e);
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		log(e);
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		log(event);
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		log(event);
	}

	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent e) {
		log(e);
	}

	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent e) {
		log(e);
	}

	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		log(e);
	}

	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		log(e);
	}

	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		// IGNORE - Too many events
	}

	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		log(e);
	}

	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		log(e);
	}

	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		log(e);
	}

}
