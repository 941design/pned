package de.markusrother.pned.gui.events;

import java.util.EventObject;

public class PlaceEditEvent extends EventObject {

	private final String placeId;
	private final int marking;

	public PlaceEditEvent(final Object source, final String placeId, final int marking) {
		super(source);
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
