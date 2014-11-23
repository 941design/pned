package de.markusrother.pned.gui;

import java.util.EventListener;

public interface EdgeEditListener
	extends
		EventListener {

	void targetComponentEntered(EdgeEditEvent e);

	void targetComponentExited(EdgeEditEvent e);

	void edgeMoved(EdgeEditEvent e);

	void edgeCancelled(EdgeEditEvent e);

	void edgeFinished(EdgeEditEvent e);

	void edgeStarted(EdgeEditEvent e);

}
