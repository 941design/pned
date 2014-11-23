package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;

public class EdgeEditEvent extends ActionEvent {

	public enum Type {
		EDGE_STARTED,
		EDGE_CHANGED,
		EDGE_CANCELLED,
		EDGE_FINISHED,
		COMPONENT_ENTERED,
		COMPONENT_EXITED,
	}

	private static final int NO_ID = 0;
	private static final String NO_COMMAND_STRING = "no command string";

	public Type getType() {
		return type;
	}

	private final Type type;

	public EdgeEditEvent(final Type type, final Object source) {
		super(source, NO_ID, NO_COMMAND_STRING);
		this.type = type;
	}

	public EdgeEditEvent(final Type type, final Object source, final Component component) {
		super(source, NO_ID, NO_COMMAND_STRING);
		this.type = type;
	}
}
