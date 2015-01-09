package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.core.PnState;

/**
 * TODO - disable when selection cancelled.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveIncomingEdgesAction extends AbstractStatefulAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove incoming edges";

	/**
	 * <p>
	 * Constructor for RemoveIncomingEdgesAction.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} object.
	 */
	public RemoveIncomingEdgesAction(final PnState state) {
		super(label, state);
		setEnabled(state.areTargetNodesSelected());
	}

	/**
	 * <p>
	 * newMenuItem.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final PnState state) {
		return new JMenuItem(new RemoveIncomingEdgesAction(state));
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedIncomingEdgeIds();
		final PnEventBus eventBus = getEventBus();
		for (final String edgeId : edgeIds) {
			eventBus.removeEdge(new EdgeRemoveCommand(this, edgeId));
		}
	}

}
