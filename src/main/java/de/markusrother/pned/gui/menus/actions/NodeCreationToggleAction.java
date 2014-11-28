package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.NodeListener;

public class NodeCreationToggleAction extends AbstractAction
	implements
		NodeListener {

	private static final String labelPrefix = "Set default node type to ";

	public static JMenuItem newMenuItem(final Object source, final NodeCreationMode initialMode) {
		return new JMenuItem(new NodeCreationToggleAction(source, initialMode));
	}

	private final Object source;
	private NodeCreationMode mode;

	NodeCreationToggleAction(final Object source, final NodeCreationMode initialMode) {
		this.source = source;
		this.mode = initialMode;
		setName();
		eventBus.addNodeListener(this);
	}

	private void setName() {
		putValue(Action.NAME, labelPrefix + mode.inverse().name().toLowerCase());
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, mode.inverse()));
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		this.mode = cmd.getMode();
		setName();
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
