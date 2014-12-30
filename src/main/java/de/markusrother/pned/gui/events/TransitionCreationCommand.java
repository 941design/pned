package de.markusrother.pned.gui.events;

import java.awt.Point;

public class TransitionCreationCommand extends AbstractNodeCreationCommand {

	public TransitionCreationCommand(final Object source, final String nodeId) {
		super(source, nodeId);
	}

	public TransitionCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source, nodeId, point);
	}

}
