package de.markusrother.pned.gui.events;

import java.util.EventObject;

import de.markusrother.pned.gui.NodeCreationMode;

public class SetNodeTypeCommand extends EventObject {

	private final NodeCreationMode mode;

	public SetNodeTypeCommand(final Object source, final NodeCreationMode mode) {
		super(source);
		this.mode = mode;
	}

	public NodeCreationMode getMode() {
		return mode;
	}

}
