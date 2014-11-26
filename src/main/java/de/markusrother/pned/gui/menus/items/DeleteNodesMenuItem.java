package de.markusrother.pned.gui.menus.items;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;

public class DeleteNodesMenuItem extends JMenuItem {

	private static final String DELETE_NODES = "Delete selected nodes";

	public static final DeleteNodesAction deleteNodesAction = new DeleteNodesAction();

	public static class DeleteNodesAction extends AbstractAction {

		DeleteNodesAction() {
			super(DELETE_NODES);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
			setEnabled(false); // Initially no nodes are selected
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.removeSelectedNodes(new RemoveSelectedNodesEvent(this));
		}
	}

	public DeleteNodesMenuItem() {
		super(deleteNodesAction);
	}
}
