package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.EdgeEditEvent;

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
