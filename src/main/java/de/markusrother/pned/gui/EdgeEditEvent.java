package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;

public class EdgeEditEvent extends ActionEvent {

	public enum Type {
		EDGE_STARTED,
		EDGE_CHANGED,
		EDGE_CANCELLED,
		EDGE_FINISHED,
		COMPONENT_ENTERED,
		COMPONENT_EXITED,
	}

	private static final int NO_ID = 0;
	private static final String NO_COMMAND_STRING = "no command string";

	private final Type type;
	private final EdgeComponent edge;
	private final Point location;
	private final Component component;

	public EdgeEditEvent(final Type type, final Object source, final EdgeComponent edge, final Point location,
			final Component component) {
		// Must pass location and component instead of MouseEvent, because
		// MouseEvent.getPoint() is component relative.
		super(source, NO_ID, NO_COMMAND_STRING);
		this.type = type;
		this.edge = edge;
		this.component = component;
		this.location = location;
	}

	public Type getType() {
		return type;
	}

	public EdgeComponent getEdge() {
		return edge;
	}

	public Point getLocation() {
		return location;
	}

	public Component getComponent() {
		return component;
	}
}
