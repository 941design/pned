package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.EdgeComponent;

public class EdgeRequest extends Request<EdgeComponent> {

	public static EdgeComponent doRequestEdge(final String edgeId, final PnRequestTarget requestTarget) {
		try {
			final EdgeRequest req = new EdgeRequest(EdgeRequest.class, edgeId);
			requestTarget.requestEdge(req);
			final EdgeComponent edge = req.get();
			return edge;
		} catch (final TimeoutException e) {
			throw new RuntimeException("TODO", e);
		}
	}

	private final String edgeId;

	public EdgeRequest(final Object source, final String edgeId) {
		super(source);
		this.edgeId = edgeId;
	}

	public SwingWorker<EdgeComponent, Object> createWorker(final EdgeRequestListener l) {
		return new EdgeRequestWorker(this, l);
	}

	public String getEdgeId() {
		return edgeId;
	}

}
