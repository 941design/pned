package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

public class RemoveSelectedNodesAction extends AbstractAction
	implements
		NodeSelectionListener {

	private static final String label = "Remove selected nodes";
	private static final int mnemonic = KeyEvent.VK_R;

	public static JMenuItem newMenuItem(final Object source, final boolean enabled) {
		return new JMenuItem(new RemoveSelectedNodesAction(source, enabled));
	}

	private final Object source;

	public RemoveSelectedNodesAction(final Object source, final boolean enabled) {
		super(label);
		this.source = source;
		putValue(Action.MNEMONIC_KEY, mnemonic);
		setEnabled(enabled);
		// FIXME - dispose!
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		eventBus.removeSelectedNodes(new RemoveSelectedNodesEvent(source));
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// IGNORE
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// IGNORE
	}

	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		setEnabled(!event.getNodes().isEmpty());
	}

	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		setEnabled(false);
	}

}
