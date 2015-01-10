package de.markusrother.pned.gui.core.model;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.core.NodeCreationMode;

public interface PnStateModel {

	PnEventBus getEventBus();

	File getCurrentDirectory();

	NodeCreationMode getNodeCreationMode();

	boolean areNodesSelected();

	boolean areSourceNodesSelected();

	boolean areTargetNodesSelected();

	Set<String> getSelectedNodeIds();

	Collection<String> getSelectedSourceNodeIds();

	Collection<String> getSelectedTargetNodeIds();

	Collection<String> getSelectedIncomingEdgeIds();

	Collection<String> getSelectedOutgoingEdgeIds();

	NodeStyleModel getPlaceStyle();

	NodeStyleModel getTransitionStyle();

	EdgeStyleModel getEdgeStyle();

	MarkingStyleModel getMarkingStyle();

}
