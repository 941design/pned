package de.markusrother.pned.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class PlaceEditListener extends MouseAdapter {

	// TODO - create static add method!

	public static PlaceEditListener INSTANCE = new PlaceEditListener();

	@Override
	public void mouseClicked(final MouseEvent e) {
		// TODO - Create RightClickListener
		if (SwingUtilities.isRightMouseButton(e)) {
			final Place place = (Place) e.getComponent();
			// TODO - First talk to the model, then to the PnGrid!
			place.setMarking("foo");
		}
		// TODO - else activate single selection (for drag drop and other).
		// TODO - broadcast select event
	}
}
