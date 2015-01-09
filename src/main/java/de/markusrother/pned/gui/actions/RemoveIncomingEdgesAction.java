package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.core.GuiState;

/**
 * TODO - disable when selection cancelled.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveIncomingEdgesAction extends AbstractGuiAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove incoming edges";

	/**
	 * <p>Constructor for RemoveIncomingEdgesAction.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param state a {@link de.markusrother.pned.gui.core.GuiState} object.
	 */
	public RemoveIncomingEdgesAction(final Object source, final GuiState state) {
		super(label, source, state);
		setEnabled(state.areTargetNodesSelected());
	}

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param state a {@link de.markusrother.pned.gui.core.GuiState} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final Object source, final GuiState state) {
		return new JMenuItem(new RemoveIncomingEdgesAction(source, state));
	}

	/** {@inheritDoc} */
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
