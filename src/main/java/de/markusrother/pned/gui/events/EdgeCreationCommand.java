package de.markusrother.pned.gui.events;

import java.util.EventObject;

public class EdgeCreationCommand extends EventObject {

	private static final String NO_EDGE_ID = null;

	private final String edgeId;
	private final String sourceId;
	private final String targetId;

	public EdgeCreationCommand(final Object source, final String sourceId, final String targetId) {
		this(source, NO_EDGE_ID, sourceId, targetId);
	}

	public EdgeCreationCommand(final Object source, final String edgeId, final String sourceId, final String targetId) {
		super(source);
		this.edgeId = edgeId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	public String getEdgeId() {
		return edgeId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

}
