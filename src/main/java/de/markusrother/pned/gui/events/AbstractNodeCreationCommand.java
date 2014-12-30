package de.markusrother.pned.gui.events;

import java.awt.Point;
import java.util.EventObject;

public abstract class AbstractNodeCreationCommand extends EventObject {

	public static final Point defaultNodeOrigin = new Point(100, 100);

	private final Point point;
	private final String nodeId;

	public AbstractNodeCreationCommand(final Object source, final String nodeId) {
		this(source, nodeId, defaultNodeOrigin);
	}

	public AbstractNodeCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source);
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
