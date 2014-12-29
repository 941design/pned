package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.SetNodeTypeCommand;

public interface NodeListener
	extends
		EventListener {

	void setCurrentNodeType(SetNodeTypeCommand cmd);

}
