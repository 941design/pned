package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;

/**
 * <p>
 * Action that triggers removal of outgoing edges for selected nodes.
 * </p>
 * <p>
 * The selection state can be retrieved from
 * {@link de.markusrother.pned.gui.control.PnState}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class RemoveOutgoingEdgesAction extends AbstractStatefulAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String name = "Remove outgoing edges";

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection
	 * triggers removal of outgoing edges for the current selection.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the current
	 *            state.
	 * @return a {@link javax.swing.JMenuItem} - the created menu item.
	 */
	public static JMenuItem newMenuItem(final PnState state) {
		return new JMenuItem(new RemoveOutgoingEdgesAction(state));
	}

	/**
	 * <p>
	 * Constructor for RemoveOutgoingEdgesAction.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the current
	 *            state.
	 */
	public RemoveOutgoingEdgesAction(final PnState state) {
		super(name, state);
		setEnabled(state.areSourceNodesSelected());
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedOutgoingEdgeIds();
		final PnEventBus eventBus = getEventBus();
		for (final String edgeId : edgeIds) {
			eventBus.removeEdge(new EdgeRemoveCommand(this, edgeId));
		}
	}

}
