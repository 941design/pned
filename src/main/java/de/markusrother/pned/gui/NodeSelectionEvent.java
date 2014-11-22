package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;
import java.util.Collection;

public class NodeSelectionEvent extends ActionEvent {

	public enum Type {
		SELECTED, //
		UNSELECTED, //
	}

	private final Collection<AbstractNode> nodes;
	private final Type type;

	public NodeSelectionEvent(final Type type, final Object source, final Collection<AbstractNode> nodes) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.type = type;
		this.nodes = nodes;
	}

	public Type getType() {
		return type;
	}

	public Collection<AbstractNode> getNodes() {
		return nodes;
	}

}
