package de.markusrother.pned.gui.events;

import java.awt.Component;
import java.awt.Point;
import java.util.EventObject;

import de.markusrother.pned.gui.components.EdgeComponent;

/**
 * <p>EdgeEditEvent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
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

	/**
	 * <p>Constructor for EdgeEditEvent.</p>
	 *
	 * @param type a {@link de.markusrother.pned.gui.events.EdgeEditEvent.Type} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param edge a {@link de.markusrother.pned.gui.components.EdgeComponent} object.
	 * @param location a {@link java.awt.Point} object.
	 * @param component a {@link java.awt.Component} object.
	 */
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

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.events.EdgeEditEvent.Type} object.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <p>Getter for the field <code>edge</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.components.EdgeComponent} object.
	 */
	public EdgeComponent getEdge() {
		return edge;
	}

	/**
	 * <p>Getter for the field <code>location</code>.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	public Point getLocation() {
		return location.getLocation();
	}

	/**
	 * <p>Getter for the field <code>component</code>.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	public Component getComponent() {
		return component;
	}
}
