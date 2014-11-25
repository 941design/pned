package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.TransitionCreationRequest;

public interface NodeListener
	extends
		EventListener {

	/**
	 * TODO - This should go to a different listener, because only the grid is
	 * interested in this.
	 */
	void nodeCreated(NodeCreationEvent e);

	/**
	 * TODO - This should go to a different listener, because only the grid is
	 * interested in this.
	 */
	void createPlace(PlaceCreationRequest e);

	/**
	 * TODO - This should go to a different listener, because only the grid is
	 * interested in this.
	 */
	void createTransition(TransitionCreationRequest e);

	void nodeRemoved(NodeRemovalEvent e);

	void removeSelectedNodes(RemoveSelectedNodesEvent e);

}
