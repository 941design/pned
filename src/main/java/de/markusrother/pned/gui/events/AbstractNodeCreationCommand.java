package de.markusrother.pned.gui.events;

import java.awt.Point;
import java.awt.event.ActionEvent;

public abstract class AbstractNodeCreationCommand extends ActionEvent {

	public static final Point defaultNodeOrigin = new Point(100, 100);

	private final Point point;
	private final String nodeId;

	public AbstractNodeCreationCommand(final Object source, final String nodeId) {
		this(source, nodeId, defaultNodeOrigin);
	}

	public AbstractNodeCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodeId = nodeId;
		this.point = point;
	}

	public String getNodeId() {
		return nodeId;
	}

	public Point getPoint() {
		return point.getLocation();
	}

}
