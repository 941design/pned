package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

public class LabelEditEvent extends ActionEvent {

	private final String elementId;
	private final String label;

	public LabelEditEvent(final Object source, final String elementId, final String label) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.elementId = elementId;
		this.label = label;
	}

	public String getElementId() {
		return elementId;
	}

	public String getLabel() {
		return label;
	}

}
