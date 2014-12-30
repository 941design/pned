package de.markusrother.pned.core;

import java.awt.Point;
import java.util.Collection;

import de.markusrother.util.JsonBuildable;

/**
 * <p>PetriNet interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNet
	extends
		JsonBuildable {

	/**
	 * <p>getPlaces.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<PlaceModel> getPlaces();

	/**
	 * <p>getTransitions.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<TransitionModel> getTransitions();

	/**
	 * <p>getEdges.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<EdgeModel> getEdges();

	/**
	 * <p>getPlace.</p>
	 *
	 * @param placeId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.core.PlaceModel} object.
	 */
	PlaceModel getPlace(String placeId);

	/**
	 * <p>getTransition.</p>
	 *
	 * @param transitionId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.core.TransitionModel} object.
	 */
	TransitionModel getTransition(String transitionId);

	/**
	 * <p>getActiveTransitions.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<TransitionModel> getActiveTransitions();

	/**
	 * <p>createPlace.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.pned.core.PlaceModel} object.
	 */
	PlaceModel createPlace(Point point);

	/**
	 * <p>createPlace.</p>
	 *
	 * @param placeId a {@link java.lang.String} object.
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.pned.core.PlaceModel} object.
	 */
	PlaceModel createPlace(String placeId, Point point);

	/**
	 * <p>createTransition.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.pned.core.TransitionModel} object.
	 */
	TransitionModel createTransition(Point point);

	/**
	 * <p>createTransition.</p>
	 *
	 * @param transitionId a {@link java.lang.String} object.
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.pned.core.TransitionModel} object.
	 */
	TransitionModel createTransition(String transitionId, Point point);

	/**
	 * <p>createEdge.</p>
	 *
	 * @param sourceId a {@link java.lang.String} object.
	 * @param targetId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.core.EdgeModel} object.
	 */
	EdgeModel createEdge(String sourceId, String targetId);

	/**
	 * <p>createEdge.</p>
	 *
	 * @param edgeId a {@link java.lang.String} object.
	 * @param sourceId a {@link java.lang.String} object.
	 * @param targetId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.core.EdgeModel} object.
	 */
	EdgeModel createEdge(String edgeId, String sourceId, String targetId);

	/**
	 * <p>setLabel.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param label a {@link java.lang.String} object.
	 */
	void setLabel(String nodeId, String label);

	/**
	 * <p>setMarking.</p>
	 *
	 * @param placeId a {@link java.lang.String} object.
	 * @param marking a int.
	 */
	void setMarking(String placeId, int marking);

	/**
	 * <p>removePlace.</p>
	 *
	 * @param place a {@link de.markusrother.pned.core.PlaceModel} object.
	 */
	void removePlace(PlaceModel place);

	/**
	 * <p>removeTransition.</p>
	 *
	 * @param transition a {@link de.markusrother.pned.core.TransitionModel} object.
	 */
	void removeTransition(TransitionModel transition);

	/**
	 * <p>removeEdge.</p>
	 *
	 * @param edge a {@link de.markusrother.pned.core.EdgeModel} object.
	 */
	void removeEdge(EdgeModel edge);

}
