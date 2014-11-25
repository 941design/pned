package de.markusrother.pned.gui.events;

import java.awt.Point;

public class TransitionCreationRequest extends AbstractNodeCreationRequest {

	public TransitionCreationRequest(final Object source) {
		super(source);
	}

	public TransitionCreationRequest(final Object source, final Point point) {
		super(source, point);
	}

}
