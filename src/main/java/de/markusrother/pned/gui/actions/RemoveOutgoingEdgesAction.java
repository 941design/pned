package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.core.PnState;

/**
 * TODO - disable when selection cancelled. Currently this object is not
 * stateful.
 *
 * FIXME - Creating new action instances saves us from listening to state
 * everywhere. We would then need a stateful factory. Stateful actions are nicer
 * in a way, because they can be freely shared, as actions should!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveOutgoingEdgesAction extends AbstractStatefulAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove outgoing edges";

	/**
	 * <p>Constructor for RemoveOutgoingEdgesAction.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param state a {@link de.markusrother.pned.gui.core.PnState} object.
	 */
	public RemoveOutgoingEdgesAction(final Object source, final PnState state) {
		super(label, source, state);
		setEnabled(state.areSourceNodesSelected());
	}

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param state a {@link de.markusrother.pned.gui.core.PnState} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final Object source, final PnState state) {
		return new JMenuItem(new RemoveOutgoingEdgesAction(source, state));
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// directly!?
		final Collection<String> edgeIds = state.getSelectedOutgoingEdgeIds();
		final PnEventBus eventBus = getEventBus();
		for (final String edgeId : edgeIds) {
			eventBus.removeEdge(new EdgeRemoveCommand(source, edgeId));
		}
	}

}
