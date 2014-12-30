package de.markusrother.pned.gui.events;

import java.awt.Component;
import java.awt.Point;
import java.util.EventObject;

import de.markusrother.pned.gui.components.EdgeComponent;

public class EdgeEditEvent extends EventObject {

	public enum Type {
		EDGE_STARTED,
		EDGE_CHANGED,
		EDGE_CANCELLED,
		EDGE_FINISHED,
		COMPONENT_ENTERED,
		COMPONENT_EXITED,
	}

	private final Type type;
	private final EdgeComponent edge;
	private final Point location;
	private final Component component;

	public EdgeEditEvent(final Type type, final Object source, final EdgeComponent edge, final Point location,
			final Component component) {
		// Must pass location and component instead of MouseEvent, because
		// MouseEvent.getPoint() is component relative.
		super(source);
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
		return location.getLocation();
	}

	public Component getComponent() {
		return component;
	}
}
