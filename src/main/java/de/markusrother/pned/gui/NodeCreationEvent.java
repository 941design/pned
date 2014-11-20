package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

import de.markusrother.concurrent.Promise;

public class NodeCreationEvent extends ActionEvent {

	private final AbstractNode node;
	private final Promise<String> nodeIdPromise;

	public NodeCreationEvent(final Object source, final AbstractNode node, final Promise<String> nodeIdPromise) {
		// TODO - create abstract class for duplex events!
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.node = node;
		this.nodeIdPromise = nodeIdPromise;
	}

	public AbstractNode getNode() {
		return node;
	}

	public void fulfillNodeIdPromise(final String value) {
		nodeIdPromise.fulfill(value);
	}

}
