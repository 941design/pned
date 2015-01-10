package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Duplex event for a concrete
 * {@link de.markusrother.pned.gui.components.AbstractNode} instance. The node
 * is expected to listen for this event and answer the request.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.requests.NodeRequestListener
 * @see de.markusrother.pned.gui.components.AbstractNode#requestNode(NodeRequest)
 */
public class NodeRequest extends Request<AbstractNode>
	implements
		JsonSerializable {

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
	public SwingWorker<AbstractNode, Object> createWorker(final NodeRequestListener l) {
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
