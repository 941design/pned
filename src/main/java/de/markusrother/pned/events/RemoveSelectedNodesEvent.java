package de.markusrother.pned.events;

import java.awt.event.ActionEvent;

public class RemoveSelectedNodesEvent extends ActionEvent {

	public RemoveSelectedNodesEvent(final Object source) {
		super(source, ActionEvent.ACTION_PERFORMED, "foo");
	}

}
