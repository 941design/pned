package de.markusrother.pned.gui;

import java.util.EventListener;

public interface NodeSelectionListener extends EventListener {

	public void nodesSelected(NodeSelectionEvent event);

	public void nodesUnselected(NodeSelectionEvent event);

}
