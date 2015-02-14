package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.LabelComponent;

public class LabelRequest extends Request<LabelComponent> {

	public static LabelComponent doRequestLabel(final String nodeId, final PnRequestTarget requestTarget) {
		try {
			final LabelRequest req = new LabelRequest(LabelRequest.class, nodeId);
			requestTarget.requestLabel(req);
			final LabelComponent label = req.get();
			return label;
		} catch (final TimeoutException e) {
			throw new RuntimeException("TODO", e);
		}
	}

	private final String nodeId;

	public LabelRequest(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	public SwingWorker<LabelComponent, Object> createWorker(final LabelRequestListener l) {
		return new LabelRequestWorker(this, l);
	}

	public String getNodeId() {
		return nodeId;
	}

}
