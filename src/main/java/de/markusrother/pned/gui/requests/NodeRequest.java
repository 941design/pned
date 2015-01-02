package de.markusrother.pned.gui.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.requests.Request;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * NodeRequest class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeRequest extends Request<AbstractNode>
	implements
		JsonSerializable {

	static class NodeRequestWorker extends SwingWorker<AbstractNode, Object> {

		protected final NodeRequest request;
		protected final NodeRequestListener listener;

		public NodeRequestWorker(final NodeRequest request, final NodeRequestListener listener) {
			this.request = request;
			this.listener = listener;
		}

		@Override
		protected AbstractNode doInBackground() throws TimeoutException {
			listener.requestNode(request);
			return request.get();
		}

		@Override
		protected void done() {
			try {
				get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO - use custom exception
				throw new RuntimeException("TODO");
			}
		}

	}

	private final String nodeId;

	/**
	 * <p>
	 * Constructor for NodeRequest.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 */
	public NodeRequest(final Object source, final String nodeId) {
		super(source);
		this.nodeId = nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNodeId() {
		return nodeId;
	}

	public SwingWorker<AbstractNode, Object> createWorker(final NodeRequestListener l) {
		return new NodeRequestWorker(this, l);
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public String toJson() {
		try {
			final JsonBuilder builder = new JsonBuilder();
			return builder.append("node", get().toJson()) // TODO - NPE!
					.toString();
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
