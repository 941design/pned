package de.markusrother.pned.gui.control.requests;

import java.util.EventListener;

public interface EdgeRequestListener
	extends
		EventListener {

	void requestEdge(EdgeRequest req);

}
