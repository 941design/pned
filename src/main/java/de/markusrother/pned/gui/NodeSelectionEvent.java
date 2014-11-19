package de.markusrother.pned.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.List;

public class NodeSelectionEvent extends ActionEvent {

	private final List<AbstractNode> nodes;

	public NodeSelectionEvent(final Container source, final List<AbstractNode> nodes) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodes = nodes;
	}

}
