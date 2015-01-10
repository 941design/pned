package de.markusrother.pned.gui.control;

import java.io.File;
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
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.control.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.control.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.core.EdgeStyle;
import de.markusrother.pned.gui.core.MarkingStyle;
import de.markusrother.pned.gui.core.NodeStyle;
import de.markusrother.pned.gui.core.model.EdgeStyleModel;
import de.markusrother.pned.gui.core.model.MarkingStyleModel;
import de.markusrother.pned.gui.core.model.NodeStyleModel;
import de.markusrother.pned.gui.core.model.PnStateModel;
import de.markusrother.pned.util.PnEventAdapter;

/**
 * <p>
 * Class representing a {@link de.markusrother.pned.gui.components.PnFrame}s
 * current state.
 * </p>
 * <p>
 * The state consists of attributes that are not directly related with the
 * underlying {@link de.markusrother.pned.core.model.PetriNetModel}, but rather
 * with its visualization.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnState extends PnEventAdapter
	implements
		PnStateModel {

	/** The default creation type for new nodes. */
	public enum NodeCreationMode {

		PLACE,
		TRANSITION;

		/** Constant <code>defaultCreationMode</code> */
		public static final NodeCreationMode defaultCreationMode = PLACE;

	}

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

	/** {@inheritDoc} */
	@Override
	public NodeCreationMode getNodeCreationMode() {
		return nodeCreationMode;
	}

	/** {@inheritDoc} */
	@Override
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
	private Set<String> filterTargetNodes(final Set<String> nodeIds) {
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
	private Set<String> filterSourceNodes(final Set<String> nodeIds) {
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
	protected Set<String> filterIncomingEdges(final Set<String> nodeIds) {
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
	protected Set<String> filterOutgoingEdges(final Set<String> nodeIds) {
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

	/** {@inheritDoc} */
	@Override
	public boolean areNodesSelected() {
		return !selectedNodeIds.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public Set<String> getSelectedTargetNodeIds() {
		return filterTargetNodes(getSelectedNodeIds());
	}

	/** {@inheritDoc} */
	@Override
	public Set<String> getSelectedSourceNodeIds() {
		return filterSourceNodes(getSelectedNodeIds());
	}

	/** {@inheritDoc} */
	@Override
	public Set<String> getSelectedIncomingEdgeIds() {
		return filterIncomingEdges(getSelectedNodeIds());
	}

	/** {@inheritDoc} */
	@Override
	public Set<String> getSelectedOutgoingEdgeIds() {
		return filterOutgoingEdges(getSelectedNodeIds());
	}

	/**
	 * TODO - Should be decoupled!
	 */
	public PnEventBus getEventBus() {
		return eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		currentDirectory = cmd.getFile();
	}

	/** {@inheritDoc} */
	@Override
	public File getCurrentDirectory() {
		return currentDirectory;
	}

	/** {@inheritDoc} */
	@Override
	public boolean areSourceNodesSelected() {
		return !getSelectedSourceNodeIds().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public boolean areTargetNodesSelected() {
		return !getSelectedTargetNodeIds().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		nodeCreationMode = cmd.getMode();
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

	/** {@inheritDoc} */
	@Override
	public NodeStyleModel getPlaceStyle() {
		return placeStyle;
	}

	/** {@inheritDoc} */
	@Override
	public NodeStyleModel getTransitionStyle() {
		return transitionStyle;
	}

	/** {@inheritDoc} */
	@Override
	public EdgeStyleModel getEdgeStyle() {
		return edgeStyle;
	}

	/** {@inheritDoc} */
	@Override
	public MarkingStyleModel getMarkingStyle() {
		return markingStyle;
	}

}
