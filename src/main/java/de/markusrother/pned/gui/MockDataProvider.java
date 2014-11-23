package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

public class MockDataProvider
	implements
		NodeListener,
		NodeSelectionListener,
		EdgeEditListener {

	private int nodeId = 1;

	public static void instantiate(final EventBus eventBus) {
		final MockDataProvider mock = new MockDataProvider();
		eventBus.addNodeListener(mock);
		eventBus.addNodeSelectionListener(mock);
		eventBus.addEdgeEditListener(mock);
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
	public void nodeCreated(final NodeCreationEvent e) {
		log(e);
		e.fulfillNodeIdPromise(String.valueOf(nodeId++));
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
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
