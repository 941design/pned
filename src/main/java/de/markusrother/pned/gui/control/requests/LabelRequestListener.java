package de.markusrother.pned.gui.control.requests;

import java.util.EventListener;

public interface LabelRequestListener
	extends
		EventListener {

	void requestLabel(LabelRequest req);

}
