package de.markusrother.pned.gui;

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
		// System.out.println(event);
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// System.out.println(event);
	}

}
