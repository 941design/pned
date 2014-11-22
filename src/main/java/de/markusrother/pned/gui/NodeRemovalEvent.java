package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;

public class NodeRemovalEvent extends ActionEvent {

	private final AbstractNode node;

	public NodeRemovalEvent(final Object source, final AbstractNode node) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.node = node;
	}

	public AbstractNode getNode() {
		return node;
	}

}
