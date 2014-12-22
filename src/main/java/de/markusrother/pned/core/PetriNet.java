package de.markusrother.pned.core;

import java.awt.Point;
import java.util.Collection;

import de.markusrother.util.JsonBuildable;

public interface PetriNet
	extends
		JsonBuildable {

	Collection<PlaceModel> getPlaces();

	Collection<TransitionModel> getTransitions();

	Collection<EdgeModel> getEdges();

	PlaceModel getPlace(String placeId);

	TransitionModel getTransition(String transitionId);

	Collection<TransitionModel> getActiveTransitions();

	PlaceModel createPlace(Point point);

	PlaceModel createPlace(String placeId, Point point);

	TransitionModel createTransition(Point point);

	TransitionModel createTransition(String transitionId, Point point);

	EdgeModel createEdge(String sourceId, String targetId);

	EdgeModel createEdge(String edgeId, String sourceId, String targetId);

	void setLabel(String nodeId, String label);

	void setMarking(String placeId, int marking);

	void removePlace(PlaceModel place);

	void removeTransition(TransitionModel transition);

	void removeEdge(EdgeModel edge);

}
