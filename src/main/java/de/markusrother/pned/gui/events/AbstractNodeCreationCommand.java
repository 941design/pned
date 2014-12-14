package de.markusrother.pned.gui.events;

import java.awt.Point;
import java.awt.event.ActionEvent;

public abstract class AbstractNodeCreationCommand extends ActionEvent {

	private static final Point defaultNodeOrigin = new Point(100, 100);
	private static final String NO_NODE_ID = null;

	private final String nodeId;
	private final Point point;

	public AbstractNodeCreationCommand(final Object source) {
		this(source, NO_NODE_ID, defaultNodeOrigin);
	}

	public AbstractNodeCreationCommand(final Object source, final String nodeId) {
		this(source, nodeId, defaultNodeOrigin);
	}

	public AbstractNodeCreationCommand(final Object source, final Point point) {
		this(source, NO_NODE_ID, point);
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
		return point;
	}

}
