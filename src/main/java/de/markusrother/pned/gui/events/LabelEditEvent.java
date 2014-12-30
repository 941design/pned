package de.markusrother.pned.gui.events;

import java.util.EventObject;

public class LabelEditEvent extends EventObject {

	private final String elementId;
	private final String label;

	public LabelEditEvent(final Object source, final String elementId, final String label) {
		super(source);
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
