package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;
import java.util.List;

// TODO - subclass generic ComponentsMovedEvent
public class NodeMovedEvent extends ActionEvent {

	private final List<AbstractNode> nodes;
	private final int deltaY;
	private final int deltaX;

	public NodeMovedEvent(final Object source, final List<AbstractNode> nodes, final int deltaX, final int deltaY) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodes = nodes;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public List<AbstractNode> getNodes() {
		return nodes;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
