package de.markusrother.pned.gui.events;

import java.awt.Point;

public class TransitionCreationRequest extends AbstractNodeCreationRequest {

	public TransitionCreationRequest(final Object source, final String nodeId) {
		super(source, nodeId);
	}

	public TransitionCreationRequest(final Object source, final String nodeId, final Point point) {
		super(source, nodeId, point);
	}

}
