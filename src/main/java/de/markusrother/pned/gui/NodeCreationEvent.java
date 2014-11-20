package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

import de.markusrother.concurrent.Promise;

public class NodeCreationEvent extends ActionEvent {

	private final AbstractNode node;
	private final Promise<String> idPromise;

	public NodeCreationEvent(final Object source, final AbstractNode node, final Promise<String> idPromise) {
		// TODO - create abstract class for duplex events!
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.node = node;
		this.idPromise = idPromise;
	}

}
