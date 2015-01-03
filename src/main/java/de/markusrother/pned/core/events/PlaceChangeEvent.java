package de.markusrother.pned.core.events;


public class PlaceChangeEvent extends PlaceEventObject {

	public PlaceChangeEvent(final Object source, final Type type, final String placeId, final int marking) {
		super(source, type, placeId, marking);
	}

}
