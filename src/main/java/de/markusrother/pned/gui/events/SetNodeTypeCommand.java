package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

import de.markusrother.pned.gui.NodeCreationMode;

// GUI-only command
public class SetNodeTypeCommand extends ActionEvent {

	private final NodeCreationMode mode;

	public SetNodeTypeCommand(final Object source, final NodeCreationMode mode) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.mode = mode;
	}

	public NodeCreationMode getMode() {
		return mode;
	}

}
