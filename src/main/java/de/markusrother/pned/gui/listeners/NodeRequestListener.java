package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.NodeRequest;

public interface NodeRequestListener
	extends
		EventListener {

	void requestNode(NodeRequest req);

}
