package de.markusrother.pned.gui;

import java.awt.event.MouseEvent;

import de.markusrother.swing.RightClickListener;

public class PlaceEditListener extends RightClickListener {

	// TODO - create static add method!

	public static PlaceEditListener INSTANCE = new PlaceEditListener();

	@Override
	public void mouseClickedRight(final MouseEvent e) {
		final Place place = (Place) e.getComponent();
		// TODO - First talk to the model, then to the PnGrid!
		place.setMarking("foo");
	}
	// TODO - else activate single selection (for drag drop and other).
	// TODO - broadcast select event
}
