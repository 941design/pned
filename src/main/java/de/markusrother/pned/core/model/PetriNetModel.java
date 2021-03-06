package de.markusrother.pned.core.model;

import de.markusrother.pned.core.NoSuchNodeException;
import de.markusrother.pned.core.TransitionInactiveException;
import de.markusrother.pned.core.UnavailableIdException;

import java.awt.*;
import java.util.Collection;

/**
 * <p>
 * Mutable model for Petri nets. Petri nets consist of collections of
 * {@link de.markusrother.pned.core.model.PlaceModel}s,
 * {@link de.markusrother.pned.core.model.TransitionModel}s, and
 * {@link de.markusrother.pned.core.model.EdgeModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNetModel {

    /**
     * <p>
     * Returns this Petri net's places.
     * </p>
     *
     * @return a {@link java.util.Collection} of
     * {@link de.markusrother.pned.core.model.PlaceModel} - all current
     * places.
     */
    Collection<PlaceModel> getPlaces();

    /**
     * <p>
     * Returns this Petri net's transitions.
     * </p>
     *
     * @return a {@link java.util.Collection} of
     * {@link de.markusrother.pned.core.model.TransitionModel} - all
     * current transitions.
     */
    Collection<TransitionModel> getTransitions();

    /**
     * <p>
     * Returns this Petri net's edges.
     * </p>
     *
     * @return a {@link java.util.Collection} of
     * {@link de.markusrother.pned.core.model.EdgeModel} - all current
     * edges.
     */
    Collection<EdgeModel> getEdges();

    /**
     * <p>
     * Returns a single place if one exists for the given identifier.
     * </p>
     *
     * @param placeId a {@link java.lang.String} - the requested unique id.
     * @return a {@link de.markusrother.pned.core.model.PlaceModel} - the
     * requested place or {@code null} if none exists with the given id.
     */
    PlaceModel getPlace(String placeId);

    /**
     * <p>
     * Returns a single transition if one exists for the given identifier.
     * </p>
     *
     * @param transitionId a {@link java.lang.String} - the requested unique id.
     * @return a {@link de.markusrother.pned.core.model.TransitionModel} - the
     * requested transition or {@code null} if none exists with the
     * given id.
     */
    TransitionModel getTransition(String transitionId);

    /**
     * <p>
     * getTransition.
     * </p>
     *
     * @param transitionId a {@link java.util.Collection} of {@link java.lang.String}s
     *                     -- the requested unique ids.
     * @return the requested transitions
     */
    Collection<TransitionModel> getTransitions(Collection<String> transitionId);

    /**
     * <p>
     * getEdge.
     * </p>
     *
     * @param edgeId a {@link java.lang.String} object.
     * @return a {@link de.markusrother.pned.core.model.EdgeModel} object.
     */
    EdgeModel getEdge(String edgeId);

    /**
     * <p>
     * Returns this Petri net's active transitions. A transition is active if
     * and only if it has no incoming edges, or if all its incoming edges'
     * sources (places) have a non-zero marking.
     * </p>
     *
     * @return a {@link java.util.Collection} of
     * {@link de.markusrother.pned.core.model.TransitionModel} - the
     * active transitions.
     */
    Collection<TransitionModel> getActiveTransitions();

    /**
     * <p>
     * Creates and returns a new place at the given coordinates.
     * </p>
     *
     * @param point a {@link java.awt.Point} - the new place's coordinates.
     * @return a {@link de.markusrother.pned.core.model.PlaceModel} - the newly
     * created place.
     */
    PlaceModel createPlace(Point point);

    /**
     * <p>
     * Creates and returns a new place with the given unique identifier at the
     * given coordinates.
     * </p>
     *
     * @param placeId a {@link java.lang.String} - the unique id.
     * @param point   a {@link java.awt.Point} - the new place's coordinates.
     * @return a {@link de.markusrother.pned.core.model.PlaceModel} - the newly
     * created place.
     * @throws de.markusrother.pned.core.UnavailableIdException if the requested id is no longer available.
     */
    PlaceModel createPlace(String placeId, Point point) throws UnavailableIdException;

    /**
     * <p>
     * Creates and returns a new transition at the given coordinates.
     * </p>
     *
     * @param point a {@link java.awt.Point} - the new transition's coordinates.
     * @return a {@link de.markusrother.pned.core.model.TransitionModel} - the
     * newly created transition.
     */
    TransitionModel createTransition(Point point);

    /**
     * <p>
     * Creates and returns a new transition with the given unique identifier at
     * the given coordinates.
     * </p>
     *
     * @param transitionId a {@link java.lang.String} - the unique id.
     * @param point        a {@link java.awt.Point} - the new transition's coordinates.
     * @return a {@link de.markusrother.pned.core.model.TransitionModel} - the
     * newly created transition.
     * @throws de.markusrother.pned.core.UnavailableIdException if the requested id is no longer available.
     */
    TransitionModel createTransition(String transitionId, Point point) throws UnavailableIdException;

    /**
     * <p>
     * Creates and returns a new edge with the given unique source and target
     * node identifiers.
     * </p>
     *
     * @param sourceId a {@link java.lang.String} - the source node's id.
     * @param targetId a {@link java.lang.String} - the target node's id.
     * @return an {@link de.markusrother.pned.core.model.EdgeModel} - the newly
     * created edge.
     * @throws de.markusrother.pned.core.NoSuchNodeException if no {@link de.markusrother.pned.core.model.NodeModel}
     *                                                       exists for either source or target identifier.
     */
    EdgeModel createEdge(String sourceId, String targetId) throws NoSuchNodeException;

    /**
     * <p>
     * Creates and returns a new edge with the given unique identifier, and
     * unique source and target node identifiers.
     * </p>
     *
     * @param edgeId   a {@link java.lang.String} - the new edge's id.
     * @param sourceId a {@link java.lang.String} - the source node's id.
     * @param targetId a {@link java.lang.String} - the target node's id.
     * @return an {@link de.markusrother.pned.core.model.EdgeModel} - the newly
     * created edge.
     * @throws de.markusrother.pned.core.UnavailableIdException if the requested edge id is no longer available.
     * @throws de.markusrother.pned.core.NoSuchNodeException    if no {@link de.markusrother.pned.core.model.NodeModel}
     *                                                          exists for either source or target identifier.
     */
    EdgeModel createEdge(String edgeId, String sourceId, String targetId) throws NoSuchNodeException,
            UnavailableIdException;

    /**
     * <p>
     * Sets label of a place uniquely identified by the provided identifier.
     * </p>
     *
     * @param nodeId a {@link java.lang.String} - the node's id.
     * @param label  a {@link java.lang.String} - the node's new label.
     * @throws de.markusrother.pned.core.NoSuchNodeException if no {@link de.markusrother.pned.core.model.NodeModel}
     *                                                       exists for the given identifier.
     */
    void setLabel(String nodeId, String label) throws NoSuchNodeException;

    /**
     * <p>
     * Sets marking of a place uniquely identified by the provided identifier.
     * </p>
     *
     * @param placeId a {@link java.lang.String} - the place's id.
     * @param marking a int - the place's new marking.
     * @throws de.markusrother.pned.core.NoSuchNodeException if no {@link de.markusrother.pned.core.model.PlaceModel}
     *                                                       exists for the given identifier.
     */
    void setMarking(String placeId, int marking) throws NoSuchNodeException;

    /**
     * <p>
     * Removes a given place.
     * </p>
     *
     * @param place a {@link de.markusrother.pned.core.model.PlaceModel} - the
     *              place to be removed.
     */
    void removePlace(PlaceModel place);

    /**
     * <p>
     * Removes a given transition.
     * </p>
     *
     * @param transition a {@link de.markusrother.pned.core.model.TransitionModel} -
     *                   the transition to be removed.
     */
    void removeTransition(TransitionModel transition);

    /**
     * <p>
     * Removes a given edge.
     * </p>
     *
     * @param edge an {@link de.markusrother.pned.core.model.EdgeModel} - the
     *             edge to be removed.
     */
    void removeEdge(EdgeModel edge);

    /**
     * <p>
     * Executes a given transition.
     * </p>
     *
     * @param transition the {@link de.markusrother.pned.core.model.TransitionModel}
     *                   transition to be fired.
     * @throws de.markusrother.pned.core.TransitionInactiveException if the given transition was not active.
     */
    void fireTransition(TransitionModel transition) throws TransitionInactiveException;

}
