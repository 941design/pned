package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.GuiEventBus;

/**
 * TODO - disable when selection cancelled. Currently this object is not
 * stateful.
 *
 * FIXME - Creating new action instances saves us from listening to state
 * everywhere. We would then need a stateful factory. Stateful actions are nicer
 * in a way, because they can be freely shared, as actions should!
 *
 */
public class RemoveOutgoingEdgesAction extends AbstractGuiAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove outgoing edges";

	public RemoveOutgoingEdgesAction(final Object source, final GuiState state) {
		super(label, source, state);
		setEnabled(state.areSourceNodesSelected());
	}

	public static JMenuItem newMenuItem(final Object source, final GuiState state) {
		return new JMenuItem(new RemoveOutgoingEdgesAction(source, state));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedOutgoingEdgeIds();
		final GuiEventBus eventBus = getEventBus();
		for (final String edgeId : edgeIds) {
			eventBus.removeEdge(new EdgeRemoveCommand(source, edgeId));
		}
	}

}
