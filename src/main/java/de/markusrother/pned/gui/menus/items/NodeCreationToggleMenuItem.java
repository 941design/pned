package de.markusrother.pned.gui.menus.items;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.NodeListener;

public class NodeCreationToggleMenuItem extends JMenuItem
	implements
		NodeListener {

	private static final String labelPrefix = "Set default node type to ";
	// TODO - make this a singleton!
	public static final NodeCreationToggleAction nodeCreationToggleAction = new NodeCreationToggleAction();

	public static class NodeCreationToggleAction extends AbstractAction
		implements
			NodeListener {

		private NodeCreationMode mode;

		NodeCreationToggleAction() {
			eventBus.addNodeListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO - Here we must subclass the surrounding JMenuItem to change
			// its text!
			eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, mode.inverse()));
		}

		@Override
		public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
			this.mode = cmd.getMode();
		}

		@Override
		public void nodeCreated(final NodeCreationEvent e) {
			// IGNORE
		}

		@Override
		public void createPlace(final PlaceCreationRequest e) {
			// IGNORE
		}

		@Override
		public void createTransition(final TransitionCreationRequest e) {
			// IGNORE
		}

		@Override
		public void nodeRemoved(final NodeRemovalEvent e) {
			// IGNORE
		}

		@Override
		public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
			// IGNORE
		}
	}

	public NodeCreationToggleMenuItem() {
		super(nodeCreationToggleAction);
		setText(NodeCreationMode.defaultCreationMode.inverse());
		eventBus.addNodeListener(this);
	}

	private void setText(final NodeCreationMode mode) {
		setText(labelPrefix + mode.name().toLowerCase());
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setText(cmd.getMode().inverse());
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

	@Override
	public void createPlace(final PlaceCreationRequest e) {
		// IGNORE
	}

	@Override
	public void createTransition(final TransitionCreationRequest e) {
		// IGNORE
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		// IGNORE
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}
}
