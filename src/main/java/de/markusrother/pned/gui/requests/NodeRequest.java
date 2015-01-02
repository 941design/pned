package de.markusrother.pned.gui.requests;

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
	 * createWorker.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.gui.listeners.NodeRequestListener}
	 *            object.
	 * @return a {@link javax.swing.SwingWorker} object.
	 */
	public SwingWorker<AbstractNode, Object> createWorker(final NodeRequestListener l) {
		return new NodeRequestWorker(this, l);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
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
