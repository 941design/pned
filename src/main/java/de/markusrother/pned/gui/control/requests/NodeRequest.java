package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Duplex event for a concrete
 * {@link de.markusrother.pned.gui.components.AbstractNodeComponent} instance.
 * The node is expected to listen for this event and answer the request.
 * </p>
 * 
 * TODO - This request is only needed by PnGrid to retrieve nodes when creating
 * edges. As the PnGrid is also (currently) responsible for creating nodes it
 * could store them in a {@code Map<String,AbstractNode>}.
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.requests.NodeRequestListener
 * @see de.markusrother.pned.gui.components.AbstractNodeComponent#requestNode(NodeRequest)
 */
public class NodeRequest extends Request<AbstractNodeComponent>
	implements
		JsonSerializable {

	/**
	 * <p>doRequestNode.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param requestTarget a {@link de.markusrother.pned.gui.control.requests.PnRequestTarget} object.
	 * @return a {@link de.markusrother.pned.gui.components.AbstractNodeComponent} object.
	 */
	public static AbstractNodeComponent doRequestNode(final String nodeId, final PnRequestTarget requestTarget) {
		try {
			final NodeRequest req = new NodeRequest(NodeRequest.class, nodeId);
			requestTarget.requestNode(req);
			final AbstractNodeComponent node = req.get();
			return node;
		} catch (final TimeoutException e) {
			throw new RuntimeException("TODO", e);
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

	/**
	 * <p>
	 * Returns a worker for a given listener that can be executed, dispatching
	 * this request asynchronously.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.gui.control.requests.NodeRequestListener}
	 *            object.
	 * @return a {@link javax.swing.SwingWorker} object.
	 */
	public SwingWorker<AbstractNodeComponent, Object> createWorker(final NodeRequestListener l) {
		return new NodeRequestWorker(this, l);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		try {
			final JsonBuilder builder = new JsonBuilder();
			return builder.append("source", source.getClass().getSimpleName()) //
					.append("node", get().toJson()) // TODO - NPE!
					.toString();
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
