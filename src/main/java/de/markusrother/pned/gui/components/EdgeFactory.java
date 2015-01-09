package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.model.EdgeStyleModel;

public interface EdgeFactory {

	EdgeComponent newEdge(String edgeId, String sourceId, String targetId);

	EdgeStyleModel getEdgeStyle();

}
