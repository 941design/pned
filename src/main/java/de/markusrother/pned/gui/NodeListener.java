package de.markusrother.pned.gui;

import java.util.EventListener;

public interface NodeListener extends EventListener {

	void nodeCreated(NodeCreationEvent e);

	void nodeRemoved(NodeRemovalEvent e);

}
