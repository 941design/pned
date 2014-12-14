package de.markusrother.pned.gui.events;

import java.awt.Point;

public class PlaceCreationRequest extends AbstractNodeCreationRequest {

	public PlaceCreationRequest(final Object source, final String nodeId) {
		super(source, nodeId);
	}

	public PlaceCreationRequest(final Object source, final String nodeId, final Point point) {
		super(source, nodeId, point);
	}

}
