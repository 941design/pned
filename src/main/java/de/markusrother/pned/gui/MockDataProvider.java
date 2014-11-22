package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

public class MockDataProvider implements NodeListener, NodeSelectionListener {

	private int nodeId = 1;

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		e.fulfillNodeIdPromise(String.valueOf(nodeId++));
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		// IGNORE
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		log(event);
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		log(event);
	}

	private void log(final ActionEvent event) {
		System.out.println(//
				event.getClass().getSimpleName() //
						+ " on " //
						+ event.getSource().getClass().getSimpleName());
		// System.out.println(event);
	}

}
