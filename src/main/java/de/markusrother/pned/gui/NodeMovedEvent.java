package de.markusrother.pned.gui;

import java.awt.event.ActionEvent;
import java.util.Collection;

// TODO - subclass generic ComponentsMovedEvent
public class NodeMovedEvent extends ActionEvent {

	private final Collection<AbstractNode> nodes;
	private final int deltaY;
	private final int deltaX;

	public NodeMovedEvent(final Object source, final Collection<AbstractNode> nodes, final int deltaX, final int deltaY) {
		// TODO - refactor to single node event!
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodes = nodes;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public Collection<AbstractNode> getNodes() {
		return nodes;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
