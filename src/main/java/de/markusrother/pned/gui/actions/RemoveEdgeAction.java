package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeoutException;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.components.EdgeComponent;
import de.markusrother.pned.gui.components.LabelComponent;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.control.requests.EdgeRequest;
import de.markusrother.pned.gui.control.requests.LabelRequest;
import de.markusrother.pned.gui.control.requests.PnRequestTarget;

/**
 * <p>RemoveEdgeAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveEdgeAction extends AbstractStatelessAction {

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.control.PnState} object.
	 * @param edgeId a {@link java.lang.String} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final PnState state, final String edgeId) {
		final PnCommandTarget commandTarget = state.getEventBus();
		final PnRequestTarget requestTarget = state.getEventBus();
		final EdgeComponent edge = EdgeRequest.doRequestEdge(edgeId, requestTarget);
		final LabelComponent sourceLabel = requestLabel(edge.getSourceId(), requestTarget);
		final LabelComponent targetLabel = requestLabel(edge.getTargetId(), requestTarget);
		final String name = "Remove edge " //
				+ "from '" + sourceLabel.getText() + "' " //
				+ "to '" + targetLabel.getText() + "' ";
		return new JMenuItem(new RemoveEdgeAction(commandTarget, edgeId, name));
	}

	/**
	 * <p>requestLabel.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param requestTarget a {@link de.markusrother.pned.gui.control.requests.PnRequestTarget} object.
	 * @return a {@link de.markusrother.pned.gui.components.LabelComponent} object.
	 */
	private static LabelComponent requestLabel(final String nodeId, final PnRequestTarget requestTarget) {
		try {
			final LabelRequest req = new LabelRequest(RemoveEdgeAction.class, nodeId);
			requestTarget.requestLabel(req);
			final LabelComponent label = req.get();
			return label;
		} catch (final TimeoutException e) {
			throw new RuntimeException("TODO", e);
		}
	}

	private final String edgeId;

	/**
	 * <p>Constructor for RemoveEdgeAction.</p>
	 *
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 * @param edgeId a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 */
	public RemoveEdgeAction(final PnCommandTarget commandTarget, final String edgeId, final String name) {
		super(commandTarget, name);
		this.edgeId = edgeId;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final EdgeRemoveCommand cmd = new EdgeRemoveCommand(this, edgeId);
		commandTarget.removeEdge(cmd);
	}

}
