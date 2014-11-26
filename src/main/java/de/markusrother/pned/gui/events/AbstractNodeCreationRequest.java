package de.markusrother.pned.gui.events;

import java.awt.Point;
import java.awt.event.ActionEvent;

// TODO - rename to command
public abstract class AbstractNodeCreationRequest extends ActionEvent {

	protected static final Point defaultNodeOrigin = new Point(100, 100);

	private final Point point;

	public AbstractNodeCreationRequest(final Object source) {
		this(source, defaultNodeOrigin);
	}

	public AbstractNodeCreationRequest(final Object source, final Point point) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

}
