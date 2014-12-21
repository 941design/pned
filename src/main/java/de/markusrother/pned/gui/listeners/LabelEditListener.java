package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.LabelEditEvent;

public interface LabelEditListener
	extends
		EventListener {

	void setLabel(LabelEditEvent e);

}
