package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Action that triggers removal of incoming edges for selected nodes.
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
public class RemoveIncomingEdgesAction extends AbstractStatefulAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String name = "Remove incoming edges";

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection
	 * triggers removal of incoming edges for the current selection.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the
	 *            current state.
	 * @return a {@link javax.swing.JMenuItem} - the created menu item.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	public static JMenuItem newMenuItem(final PnState state, final PnCommandTarget commandTarget) {
		return new JMenuItem(new RemoveIncomingEdgesAction(state, commandTarget));
	}

	/**
	 * <p>
	 * Constructor for RemoveIncomingEdgesAction.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the
	 *            current state.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	public RemoveIncomingEdgesAction(final PnState state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, name);
		setEnabled(state.areTargetNodesSelected());
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedIncomingEdgeIds();
		for (final String edgeId : edgeIds) {
			commandTarget.removeEdge(new EdgeRemoveCommand(this, edgeId));
		}
	}

}
