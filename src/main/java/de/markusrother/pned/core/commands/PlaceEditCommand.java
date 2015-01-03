package de.markusrother.pned.core.commands;

import de.markusrother.pned.core.events.PlaceEventObject;

public class PlaceEditCommand extends PlaceEventObject {

	public PlaceEditCommand(final Object source, final Type type, final String placeId, final int marking) {
		super(source, type, placeId, marking);
	}

}
