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
import de.markusrother.util.JsonBuildable;
import de.markusrother.util.JsonBuilder;

/**
 * <p>PetriNetImpl class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "net")
@XmlType(propOrder = { "nodes", "edges" })
public class PetriNetImpl
	implements
		PetriNet {

	protected final Collection<PlaceModel> places;
	protected final Collection<TransitionModel> transitions;
	protected final Collection<EdgeModel> edges;

	private int elementCount = 0;

	/**
	 * <p>Constructor for PetriNetImpl.</p>
	 */
	public PetriNetImpl() {
		this.places = new LinkedList<>();
		this.transitions = new LinkedList<>();
		this.edges = new LinkedList<>();
	}

	/**
	 * <p>createId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String createId() {
		String elementId;
		do {
			elementId = String.valueOf(++elementCount);
		} while (!isIdAvailable(elementId));
		return elementId;
	}

	/**
	 * <p>isIdAvailable.</p>
	 *
	 * @param elementId a {@link java.lang.String} object.
	 * @return a boolean.
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
		return createPlace(createId(), point);
	}

	/** {@inheritDoc} */
	@Override
	public PlaceModel createPlace(final String placeId, final Point point) {
		if (placeId == null || point == null || !isIdAvailable(placeId)) {
			throw new IllegalArgumentException();
		}
		final PlaceModel place = new PlaceImpl(placeId, point);
		places.add(place);
		return place;
	}

	/** {@inheritDoc} */
	@Override
	public TransitionModel createTransition(final Point point) {
		return createTransition(createId(), point);
	}

	/** {@inheritDoc} */
	@Override
	public TransitionModel createTransition(final String transitionId, final Point point) {
		if (transitionId == null || point == null) {
			throw new IllegalArgumentException();
		} else if (!isIdAvailable(transitionId)) {
			throw new IllegalArgumentException("Invalid transition id: " + transitionId);
		}
		final TransitionModel transition = new TransitionImpl(transitionId, point);
		transitions.add(transition);
		return transition;
	}

	/** {@inheritDoc} */
	@Override
	public EdgeModel createEdge(final String sourceId, final String targetId) {
		return createEdge(createId(), sourceId, targetId);
	}

	/** {@inheritDoc} */
	@Override
	public EdgeModel createEdge(final String edgeId, final String sourceId, final String targetId) {
		if (edgeId == null || sourceId == null || targetId == null) {
			throw new IllegalArgumentException();
		} else if (!isIdAvailable(edgeId)) {
			throw new IllegalArgumentException("Invalid edge id: " + edgeId);
		} else if (!nodeExists(sourceId)) {
			throw new NoSuchNodeException(sourceId);
		} else if (!nodeExists(targetId)) {
			throw new NoSuchNodeException(targetId);
		}
		final EdgeModel edge = new EdgeImpl(edgeId, sourceId, targetId);
		edges.add(edge);
		return edge;
	}

	/**
	 * <p>nodeExists.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @return a boolean.
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
	 * <p>getNodes.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	@XmlAnyElement
	protected Collection<NodeModel> getNodes() {
		final Collection<NodeModel> nodes = new ArrayList<>(places.size() + transitions.size());
		nodes.addAll(places);
		nodes.addAll(transitions);
		return nodes;
	}

	/**
	 * <p>getNode.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.core.NodeModel} object.
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
	public void setLabel(final String nodeId, final String label) {
		final NodeModel node = getNode(nodeId);
		if (node == null) {
			throw new IllegalArgumentException("Invalid node id: " + nodeId);
		}
		setLabel(node, label);
	}

	/** {@inheritDoc} */
	protected void setLabel(final NodeModel node, final String label) {
		node.setLabel(label);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final String placeId, final int marking) {
		final PlaceModel place = getPlace(placeId);
		if (place == null) {
			throw new IllegalArgumentException("Invalid place id: " + placeId);
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
	 * <p>removeAjdacentEdges.</p>
	 *
	 * @param node a {@link de.markusrother.pned.core.NodeModel} object.
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
