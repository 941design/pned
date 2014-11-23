package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

import de.markusrother.pned.gui.components.AbstractNode;

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
