package de.markusrother.pned.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.markusrother.pned.core.exceptions.NoSuchNodeException;
import de.markusrother.pned.core.exceptions.UnavailableIdException;
import de.markusrother.util.JsonBuildable;
import de.markusrother.util.JsonBuilder;

/**
 * <p>
 * Default implementation of {@link PetriNetModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "net")
@XmlType(propOrder = { "nodes", "edges" })
public class DefaultPetriNet
	implements
		PetriNetModel,
		JsonBuildable {

	/** All of this Petri net's current places. */
	protected final Collection<PlaceModel> places;
	/** All of this Petri net's current transitions. */
	protected final Collection<TransitionModel> transitions;
	/** All of this Petri net's current edges. */
	protected final Collection<EdgeModel> edges;

	/** Running count of created elements, used for unique id generation. */
	private int elementCount = 0;

	/**
	 * <p>
	 * Constructor for PetriNetImpl.
	 * </p>
	 */
	public DefaultPetriNet() {
		this.places = new LinkedList<>();
		this.transitions = new LinkedList<>();
		this.edges = new LinkedList<>();
	}

	/**
	 * <p>
	 * Creates and returns a new unique identifier.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - a newly created id.
	 */
	protected String createId() {
		String elementId;
		do {
			elementId = String.valueOf(++elementCount);
		} while (!isIdAvailable(elementId));
		return elementId;
	}

	/**
	 * <p>
	 * Tests whether the given identifier can be used for new elements.
	 * </p>
	 *
	 * @param elementId
	 *            a {@link java.lang.String} - the requested id.
	 * @return a boolean - true if provided id is still unused, false otherwise.
	 */
	private boolean isIdAvailable(final String elementId) {
		for (final NodeModel node : getNodes()) {
			if (node.hasId(elementId)) {
				return false;
			}
		}
		for (final EdgeModel edge : edges) {
			if (edge.hasId(elementId)) {
				return false;
			}
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<PlaceModel> getPlaces() {
		return Collections.unmodifiableCollection(places);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<TransitionModel> getTransitions() {
		return Collections.unmodifiableCollection(transitions);
	}

	/** {@inheritDoc} */
	@Override
	@XmlAnyElement
	public Collection<EdgeModel> getEdges() {
		return Collections.unmodifiableCollection(edges);
	}

	/** {@inheritDoc} */
	@Override
	public PlaceModel createPlace(final Point point) {
		try {
			return createPlace(createId(), point);
		} catch (final UnavailableIdException e) {
			// Should never happen.
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public PlaceModel createPlace(final String placeId, final Point point) throws UnavailableIdException {
		if (placeId == null || point == null) {
			throw new IllegalArgumentException();
		}
		if (!isIdAvailable(placeId)) {
			throw new UnavailableIdException(placeId);
		}
		final PlaceModel place = new DefaultPlace(placeId, point);
		places.add(place);
		return place;
	}

	/** {@inheritDoc} */
	@Override
	public TransitionModel createTransition(final Point point) {
		try {
			return createTransition(createId(), point);
		} catch (final UnavailableIdException e) {
			// Should never happen.
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public TransitionModel createTransition(final String transitionId, final Point point) throws UnavailableIdException {
		if (transitionId == null || point == null) {
			throw new IllegalArgumentException();
		} else if (!isIdAvailable(transitionId)) {
			throw new UnavailableIdException(transitionId);
		}
		final TransitionModel transition = new DefaultTransition(transitionId, point);
		transitions.add(transition);
		return transition;
	}

	/** {@inheritDoc} */
	@Override
	public EdgeModel createEdge(final String sourceId, final String targetId) throws NoSuchNodeException {
		try {
			return createEdge(createId(), sourceId, targetId);
		} catch (final UnavailableIdException e) {
			// Should never happen.
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public EdgeModel createEdge(final String edgeId, final String sourceId, final String targetId)
			throws UnavailableIdException, NoSuchNodeException {
		if (edgeId == null || sourceId == null || targetId == null) {
			throw new IllegalArgumentException();
		} else if (!isIdAvailable(edgeId)) {
			throw new UnavailableIdException(edgeId);
		} else if (!nodeExists(sourceId)) {
			throw new NoSuchNodeException(sourceId);
		} else if (!nodeExists(targetId)) {
			throw new NoSuchNodeException(targetId);
		}
		final EdgeModel edge = new DefaultEdge(edgeId, sourceId, targetId);
		edges.add(edge);
		return edge;
	}

	/**
	 * <p>
	 * Tests whether a node with the given identifier exists.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} - the requested id.
	 * @return a boolean - true if node exists, false otherwise.
	 */
	private boolean nodeExists(final String nodeId) {
		for (final NodeModel node : getNodes()) {
			if (nodeId.equals(node.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Returns this Petri net's places and transitions.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} of {@link NodeModel} - all current
	 *         nodes.
	 */
	@XmlAnyElement
	protected Collection<NodeModel> getNodes() {
		final Collection<NodeModel> nodes = new ArrayList<>(places.size() + transitions.size());
		nodes.addAll(places);
		nodes.addAll(transitions);
		return nodes;
	}

	/**
	 * <p>
	 * Returns a single node if one exists for the given identifier.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} - the requested unique id.
	 * @return a {@link de.markusrother.pned.core.NodeModel} - the requested
	 *         node or {@code null} if none exists with the given id.
	 */
	private NodeModel getNode(final String nodeId) {
		for (final NodeModel node : getNodes()) {
			if (node.hasId(nodeId)) {
				return node;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public PlaceModel getPlace(final String placeId) {
		for (final PlaceModel place : places) {
			if (place.hasId(placeId)) {
				return place;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public TransitionModel getTransition(final String transitionId) {
		for (final TransitionModel transition : transitions) {
			if (transition.hasId(transitionId)) {
				return transition;
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<TransitionModel> getActiveTransitions() {
		final Collection<TransitionModel> active = new LinkedList<>(transitions);
		for (final EdgeModel edge : edges) {
			final TransitionModel target = getTransition(edge.getTargetId());
			if (target != null) {
				final PlaceModel place = getPlace(edge.getSourceId());
				if (place.getMarking() < 1) {
					active.remove(target);
				}
			}
		}
		return active;
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final String nodeId, final String label) throws NoSuchNodeException {
		final NodeModel node = getNode(nodeId);
		if (node == null) {
			throw new NoSuchNodeException(nodeId);
		}
		setLabel(node, label);
	}

	/** {@inheritDoc} */
	protected void setLabel(final NodeModel node, final String label) {
		node.setLabel(label);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final String placeId, final int marking) throws NoSuchNodeException {
		final PlaceModel place = getPlace(placeId);
		if (place == null) {
			throw new NoSuchNodeException(placeId);
		}
		setMarking(place, marking);
	}

	/** {@inheritDoc} */
	protected void setMarking(final PlaceModel place, final int marking) {
		place.setMarking(marking);
	}

	/** {@inheritDoc} */
	@Override
	public void removePlace(final PlaceModel place) {
		places.remove(place);
		removeAjdacentEdges(place);
	}

	/** {@inheritDoc} */
	@Override
	public void removeTransition(final TransitionModel transition) {
		transitions.remove(transition);
		removeAjdacentEdges(transition);
	}

	/**
	 * <p>
	 * Removes all adjacent (incoming and outgoing) edges of the given node.
	 * </p>
	 *
	 * @param node
	 *            a {@link de.markusrother.pned.core.NodeModel} - from which to
	 *            remove its edges.
	 */
	private void removeAjdacentEdges(final NodeModel node) {
		final String nodeId = node.getId();
		final Iterator<EdgeModel> edgeIter = edges.iterator();
		while (edgeIter.hasNext()) {
			final EdgeModel edge = edgeIter.next();
			if (nodeId.equals(edge.getSourceId()) || nodeId.equals(edge.getTargetId())) {
				edgeIter.remove();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdge(final EdgeModel edge) {
		edges.remove(edge);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder jb = new JsonBuilder();
		return jb.appendList("places", places.toArray(new JsonBuildable[places.size()])) //
				.appendList("transitions", transitions.toArray(new JsonBuildable[transitions.size()])) //
				.appendList("edges", edges.toArray(new JsonBuildable[edges.size()])) //
				.toString();
	}
}
