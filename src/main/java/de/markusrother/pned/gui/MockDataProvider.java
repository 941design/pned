package de.markusrother.pned.gui;

public class MockDataProvider implements NodeCreationListener {

	private int nodeId = 1;

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		e.fulfillNodeIdPromise(String.valueOf(nodeId++));
	}

}
