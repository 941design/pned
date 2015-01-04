package de.markusrother.pned.gui.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

public class AbstractMenuFactory
	implements
		NodeSelectionListener,
		NodeRemovalListener,
		EdgeCreationListener {

	protected final Set<String> selectedNodeIds;
	protected final Map<String, String> incomingEdgesMap;
	protected GuiEventBus eventBus;
	protected NodeCreationMode nodeCreationMode;
	protected boolean areNodesSelected;

	public AbstractMenuFactory(final GuiEventBus eventBus) {
		this.eventBus = eventBus;
		this.areNodesSelected = false;
		this.nodeCreationMode = NodeCreationMode.defaultCreationMode;
		this.selectedNodeIds = new HashSet<>();
		this.incomingEdgesMap = new HashMap<>();

		installListeners();
	}

	private void installListeners() {
		eventBus.addListener(NodeSelectionListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	private void suspendListeners() {
		eventBus.removeListener(NodeSelectionListener.class, this);
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(EdgeCreationListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeMultiSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent event) {
		areNodesSelected = !event.getNodes().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent event) {
		areNodesSelected = false;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventMulticaster</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	protected void setEventBus(final GuiEventBus eventBus) {
		if (this.eventBus != null) {
			suspendListeners();
		}
		this.eventBus = eventBus;
		installListeners();
	}

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final String targetId = cmd.getTargetId();
		incomingEdgesMap.put(edgeId, targetId);
	}

	@Override
	public void nodeRemoved(final NodeRemovalCommand cmd) {
		areNodesSelected = false;
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent cmd) {
		areNodesSelected = false;
	}

}
