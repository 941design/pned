package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;

public class PlaceEditEvent extends ActionEvent {

	private final String placeId;
	private final int marking;

	public PlaceEditEvent(final Object source, final String placeId, final int marking) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.placeId = placeId;
		this.marking = marking;
	}

	public String getPlaceId() {
		return placeId;
	}

	public int getMarking() {
		return marking;
	}

}
