package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.IdRequest;

public interface IdRequestListener
	extends
		EventListener {

	void requestId(IdRequest req);

}
