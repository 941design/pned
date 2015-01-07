package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.GuiEventBus;

/**
 * TODO - disable when selection cancelled.
 *
 */
public class RemoveIncomingEdgesAction extends AbstractGuiAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove incoming edges";

	public RemoveIncomingEdgesAction(final Object source, final GuiState state) {
		super(label, source, state);
		setEnabled(state.areTargetNodesSelected());
	}

	public static JMenuItem newMenuItem(final Object source, final GuiState state) {
		return new JMenuItem(new RemoveIncomingEdgesAction(source, state));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedIncomingEdgeIds();
		final GuiEventBus eventBus = getEventBus();
		for (final String edgeId : edgeIds) {
			eventBus.removeEdge(new EdgeRemoveCommand(source, edgeId));
		}
	}

}
