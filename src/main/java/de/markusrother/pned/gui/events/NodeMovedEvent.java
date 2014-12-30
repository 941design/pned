package de.markusrother.pned.gui.events;

import java.util.Collection;
import java.util.EventObject;

public class NodeMovedEvent extends EventObject {

	private final Collection<String> nodeIds;
	private final int deltaY;
	private final int deltaX;

	public NodeMovedEvent(final Object source, final Collection<String> nodeIds, final int deltaX, final int deltaY) {
		// TODO - refactor to single node event!
		// FIXME - Should probably take nodePromise!
		super(source);
		this.nodeIds = nodeIds;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}

	public Collection<String> getNodeIds() {
		return nodeIds;
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
