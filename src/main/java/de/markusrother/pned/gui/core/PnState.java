package de.markusrother.pned.gui.core;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.model.EdgeStyleModel;
import de.markusrother.pned.gui.model.MarkingStyleModel;
import de.markusrother.pned.gui.model.NodeStyleModel;
import de.markusrother.pned.gui.style.EdgeStyle;
import de.markusrother.pned.gui.style.MarkingStyle;
import de.markusrother.pned.gui.style.NodeStyle;
import de.markusrother.pned.util.PnEventAdapter;

/**
 * <p>
 * GuiState class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnState extends PnEventAdapter {

	protected final NodeStyleModel placeStyle;
	protected final NodeStyleModel transitionStyle;
	protected final MarkingStyleModel markingStyle;
	protected final EdgeStyleModel edgeStyle;
	protected final Set<String> selectedNodeIds;
	protected final Map<String, String> incomingEdgesMap;
	protected final Map<String, String> outgoingEdgesMap;

	/** The current directory in which to do file operations. */
	protected File currentDirectory;
	protected NodeCreationMode nodeCreationMode;

	/**
	 * <p>
	 * Constructor for GuiState.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.PnEventBus} object.
	 */
	public PnState(final PnEventBus eventBus) {
		this.placeStyle = NodeStyle.newDefault();
		this.transitionStyle = NodeStyle.newDefault();
		this.markingStyle = MarkingStyle.newDefault();
		this.edgeStyle = EdgeStyle.newDefault();
		this.nodeCreationMode = NodeCreationMode.defaultCreationMode;
		this.selectedNodeIds = new HashSet<>();
		this.incomingEdgesMap = new HashMap<>();
		this.outgoingEdgesMap = new HashMap<>();
		setEventBus(eventBus);
	}

	/** {@inheritDoc} */
	@Override
	protected void process(final EventObject e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent event) {
		for (final AbstractNode node : event.getNodes()) {
			selectedNodeIds.add(node.getId());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent event) {
		selectedNodeIds.clear();
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final String targetId = cmd.getTargetId();
		final String sourceId = cmd.getSourceId();
		incomingEdgesMap.put(edgeId, targetId);
		outgoingEdgesMap.put(edgeId, sourceId);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand cmd) {
		selectedNodeIds.clear();
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent cmd) {
		selectedNodeIds.clear();
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeCreationMode</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.NodeCreationMode} object.
	 */
	public NodeCreationMode getNodeCreationMode() {
		return nodeCreationMode;
	}

	/**
	 * <p>
	 * Setter for the field <code>nodeCreationMode</code>.
	 * </p>
	 *
	 * @param nodeCreationMode
	 *            a {@link de.markusrother.pned.gui.NodeCreationMode} object.
	 */
	public void setNodeCreationMode(final NodeCreationMode nodeCreationMode) {
		this.nodeCreationMode = nodeCreationMode;
	}

	/**
	 * <p>
	 * Getter for the field <code>selectedNodeIds</code>.
	 * </p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<String> getSelectedNodeIds() {
		return selectedNodeIds;
	}

	/**
	 * <p>
	 * filterTargetNodes.
	 * </p>
	 *
	 * @param nodeIds
	 *            a {@link java.util.Set} object.
	 * @return a {@link java.util.Collection} object.
	 */
	private Collection<String> filterTargetNodes(final Set<String> nodeIds) {
		final Set<String> targetNodeIds = new HashSet<>();
		for (final String targetId : incomingEdgesMap.values()) {
			if (nodeIds.contains(targetId)) {
				targetNodeIds.add(targetId);
			}
		}
		return Collections.unmodifiableSet(targetNodeIds);
	}

	/**
	 * <p>
	 * filterSourceNodes.
	 * </p>
	 *
	 * @param nodeIds
	 *            a {@link java.util.Set} object.
	 * @return a {@link java.util.Collection} object.
	 */
	private Collection<String> filterSourceNodes(final Set<String> nodeIds) {
		final Set<String> sourceNodeIds = new HashSet<>();
		for (final String sourceId : outgoingEdgesMap.values()) {
			if (nodeIds.contains(sourceId)) {
				sourceNodeIds.add(sourceId);
			}
		}
		return Collections.unmodifiableSet(sourceNodeIds);
	}

	/**
	 * <p>
	 * filterIncomingEdges.
	 * </p>
	 *
	 * @param nodeIds
	 *            a {@link java.util.Set} object.
	 * @return a {@link java.util.Collection} object.
	 */
	protected Collection<String> filterIncomingEdges(final Set<String> nodeIds) {
		final Set<String> edgeIds = new HashSet<>();
		for (final Entry<String, String> entry : incomingEdgesMap.entrySet()) {
			final String edgeId = entry.getKey();
			final String targetId = entry.getValue();
			if (nodeIds.contains(targetId)) {
				edgeIds.add(edgeId);
			}
		}
		return Collections.unmodifiableSet(edgeIds);
	}

	/**
	 * <p>
	 * filterOutgoingEdges.
	 * </p>
	 *
	 * @param nodeIds
	 *            a {@link java.util.Set} object.
	 * @return a {@link java.util.Collection} object.
	 */
	protected Collection<String> filterOutgoingEdges(final Set<String> nodeIds) {
		final Set<String> edgeIds = new HashSet<>();
		for (final Entry<String, String> entry : outgoingEdgesMap.entrySet()) {
			final String edgeId = entry.getKey();
			final String targetId = entry.getValue();
			if (nodeIds.contains(targetId)) {
				edgeIds.add(edgeId);
			}
		}
		return Collections.unmodifiableSet(edgeIds);
	}

	/**
	 * <p>
	 * areNodesSelected.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean areNodesSelected() {
		return !selectedNodeIds.isEmpty();
	}

	/**
	 * <p>
	 * getSelectedTargetNodeIds.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getSelectedTargetNodeIds() {
		return filterTargetNodes(getSelectedNodeIds());
	}

	/**
	 * <p>
	 * getSelectedSourceNodeIds.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getSelectedSourceNodeIds() {
		return filterSourceNodes(getSelectedNodeIds());
	}

	/**
	 * <p>
	 * getSelectedIncomingEdgeIds.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getSelectedIncomingEdgeIds() {
		return filterIncomingEdges(getSelectedNodeIds());
	}

	/**
	 * <p>
	 * getSelectedOutgoingEdgeIds.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<String> getSelectedOutgoingEdgeIds() {
		return filterOutgoingEdges(getSelectedNodeIds());
	}

	/**
	 * <p>
	 * getEventBus.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.PnEventBus} object.
	 */
	public PnEventBus getEventBus() {
		return eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		currentDirectory = cmd.getFile();
	}

	/**
	 * <p>
	 * Getter for the field <code>currentDirectory</code>.
	 * </p>
	 *
	 * @return a {@link java.io.File} object.
	 */
	public File getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * <p>
	 * areSourceNodesSelected.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean areSourceNodesSelected() {
		return !getSelectedSourceNodeIds().isEmpty();
	}

	/**
	 * <p>
	 * areTargetNodesSelected.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean areTargetNodesSelected() {
		return !getSelectedTargetNodeIds().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		switch (cmd.getMode()) {
		case PLACE:
			// removeState(State.TRANSITION_CREATION);
			// addState(State.PLACE_CREATION);
			break;
		case TRANSITION:
			// removeState(State.PLACE_CREATION);
			// addState(State.TRANSITION_CREATION);
			break;
		default:
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdge(final EdgeRemoveCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		incomingEdgesMap.remove(edgeId);
		outgoingEdgesMap.remove(edgeId);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		final int size = cmd.getSize();
		placeStyle.setSize(size);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		final int size = cmd.getSize();
		transitionStyle.setSize(size);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		final int size = cmd.getSize();
		markingStyle.setSize(size);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
		final int size = cmd.getSize();
		edgeStyle.setSize(size);
	}

	/**
	 * <p>
	 * Getter for the field <code>placeStyle</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.model.NodeStyleModel} object.
	 */
	public NodeStyleModel getPlaceStyle() {
		return placeStyle;
	}

	/**
	 * <p>
	 * Getter for the field <code>transitionStyle</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.model.NodeStyleModel} object.
	 */
	public NodeStyleModel getTransitionStyle() {
		return transitionStyle;
	}

	/**
	 * <p>Getter for the field <code>edgeStyle</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.model.EdgeStyleModel} object.
	 */
	public EdgeStyleModel getEdgeStyle() {
		return edgeStyle;
	}

	/**
	 * <p>Getter for the field <code>markingStyle</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.model.MarkingStyleModel} object.
	 */
	public MarkingStyleModel getMarkingStyle() {
		return markingStyle;
	}

}
