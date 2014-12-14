package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.EdgeCreationCommand;

public interface EdgeCreationListener
	extends
		EventListener {

	void createEdge(EdgeCreationCommand cmd);

}
