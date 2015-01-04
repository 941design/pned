package de.markusrother.pned.gui.listeners;

import java.awt.event.MouseEvent;

import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.gui.components.NodeLabel;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.RightClickListener;

/**
 * <p>NodeLabelEditor class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabelEditor extends RightClickListener
	implements
		NodeCreationListener,
		NodeSelectionListener {

	private NodeLabel currentLabel;

	/**
	 * <p>Constructor for NodeLabelEditor.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	public NodeLabelEditor(final GuiEventBus eventBus) {
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	private void startEditLabel(final NodeLabel nodeLabel) {
		if (currentLabel == nodeLabel) {
			return;
		} else if (currentLabel != null) {
			cancelEditLabel();
		}
		currentLabel = nodeLabel;
		currentLabel.startEditLabel();
	}

	private void cancelEditLabel() {
		if (currentLabel != null) {
			currentLabel.cancelEditLabel();
			currentLabel = null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedRight(final MouseEvent e) {
		final NodeLabel nodeLabel = (NodeLabel) e.getSource();
		startEditLabel(nodeLabel);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent e) {
		cancelEditLabel();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		cancelEditLabel();
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		cancelEditLabel();
	}

}
