package de.markusrother.pned.gui.events;

import java.awt.Point;

public class PlaceCreationCommand extends AbstractNodeCreationCommand {

	public PlaceCreationCommand(final Object source, final String nodeId) {
		super(source, nodeId);
	}

	public PlaceCreationCommand(final Object source, final Point point) {
		super(source, point);
	}

}
