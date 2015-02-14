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

public class RemoveEdgeAction extends AbstractStatelessAction {

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

	public RemoveEdgeAction(final PnCommandTarget commandTarget, final String edgeId, final String name) {
		super(commandTarget, name);
		this.edgeId = edgeId;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final EdgeRemoveCommand cmd = new EdgeRemoveCommand(this, edgeId);
		commandTarget.removeEdge(cmd);
	}

}
