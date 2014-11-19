package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;
import java.util.List;

public class NodeSelectionEvent extends ActionEvent {

	public enum Type {
		SELECTED, //
		UNSELECTED, //
	}

	private final List<AbstractNode> nodes;
	private final Type type;

	public NodeSelectionEvent(final Type type, final Object source, final List<AbstractNode> nodes) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.type = type;
		this.nodes = nodes;
	}

	public Type getType() {
		return type;
	}

	public List<AbstractNode> getNodes() {
		return nodes;
	}

}
