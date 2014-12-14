package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PlaceEditEvent;

public interface PlaceEditListener
	extends
		EventListener {

	void setMarking(PlaceEditEvent cmd);

}
