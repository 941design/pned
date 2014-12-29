package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

public class RemoveSelectedNodesAction extends AbstractAction
	implements
		NodeSelectionListener {

	private static final String label = "Remove selected nodes";
	private static final int mnemonic = KeyEvent.VK_R;

	public static JMenuItem newMenuItem(final EventBus eventMulticaster, final Object source, final boolean enabled) {
		return new JMenuItem(new RemoveSelectedNodesAction(eventMulticaster, source, enabled));
	}

	private final Object source;
	private final EventBus eventBus;

	public RemoveSelectedNodesAction(final EventBus eventMulticaster, final Object source, final boolean enabled) {
		super(label);
		this.eventBus = eventMulticaster;
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
