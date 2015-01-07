package de.markusrother.pned.gui.actions;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.util.PetriNetGuiEventAdapter;

public class GuiState extends PetriNetGuiEventAdapter {

	public NodeCreationMode getNodeCreationMode() {
		return nodeCreationMode;
	}

	public void setNodeCreationMode(final NodeCreationMode nodeCreationMode) {
		this.nodeCreationMode = nodeCreationMode;
	}

	public Set<String> getSelectedNodeIds() {
		return selectedNodeIds;
	}

	protected final Set<String> selectedNodeIds;
	protected final Map<String, String> incomingEdgesMap;
	protected final Map<String, String> outgoingEdgesMap;

	/** The current directory in which to do file operations. */
	protected File currentDirectory;
	protected NodeCreationMode nodeCreationMode;

	public GuiState(final GuiEventBus eventBus) {
		this.nodeCreationMode = NodeCreationMode.defaultCreationMode;
		this.selectedNodeIds = new HashSet<>();
		this.incomingEdgesMap = new HashMap<>();
		this.outgoingEdgesMap = new HashMap<>();
		setEventBus(eventBus);
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

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final String targetId = cmd.getTargetId();
		final String sourceId = cmd.getSourceId();
		incomingEdgesMap.put(edgeId, targetId);
		outgoingEdgesMap.put(edgeId, sourceId);
	}

	@Override
	public void nodeRemoved(final NodeRemovalCommand cmd) {
		selectedNodeIds.clear();
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent cmd) {
		selectedNodeIds.clear();
	}

	private Collection<String> filterTargetNodes(final Set<String> nodeIds) {
		final Set<String> targetNodeIds = new HashSet<>();
		for (final String targetId : incomingEdgesMap.values()) {
			if (nodeIds.contains(targetId)) {
				targetNodeIds.add(targetId);
			}
		}
		return Collections.unmodifiableSet(targetNodeIds);
	}

	private Collection<String> filterSourceNodes(final Set<String> nodeIds) {
		final Set<String> sourceNodeIds = new HashSet<>();
		for (final String sourceId : outgoingEdgesMap.values()) {
			if (nodeIds.contains(sourceId)) {
				sourceNodeIds.add(sourceId);
			}
		}
		return Collections.unmodifiableSet(sourceNodeIds);
	}

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

	public boolean areNodesSelected() {
		return !selectedNodeIds.isEmpty();
	}

	public Collection<String> getSelectedTargetNodeIds() {
		return filterTargetNodes(getSelectedNodeIds());
	}

	public Collection<String> getSelectedSourceNodeIds() {
		return filterSourceNodes(getSelectedNodeIds());
	}

	public Collection<String> getSelectedIncomingEdgeIds() {
		return filterIncomingEdges(getSelectedNodeIds());
	}

	public Collection<String> getSelectedOutgoingEdgeIds() {
		return filterOutgoingEdges(getSelectedNodeIds());
	}

	public GuiEventBus getEventBus() {
		return eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		currentDirectory = cmd.getFile();
	}

	public File getCurrentDirectory() {
		return currentDirectory;
	}

	public boolean areSourceNodesSelected() {
		return !getSelectedSourceNodeIds().isEmpty();
	}

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

	@Override
	public void removeEdge(final EdgeRemoveCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		incomingEdgesMap.remove(edgeId);
		outgoingEdgesMap.remove(edgeId);
	}

	@Override
	protected void process(final EventObject e) {
		// IGNORE
	}

}
