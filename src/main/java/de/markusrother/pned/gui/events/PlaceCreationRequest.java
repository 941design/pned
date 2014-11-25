package de.markusrother.pned.gui.events;

import java.awt.Point;

public class PlaceCreationRequest extends AbstractNodeCreationRequest {

	public PlaceCreationRequest(final Object source) {
		super(source);
	}

	public PlaceCreationRequest(final Object source, final Point point) {
		super(source, point);
	}

}
