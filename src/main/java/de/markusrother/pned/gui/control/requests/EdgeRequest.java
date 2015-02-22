package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.requests.Request;
import de.markusrother.pned.gui.components.EdgeComponent;

/**
 * <p>
 * EdgeRequest class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.requests.EdgeRequestListener
 * @see de.markusrother.pned.gui.components.EdgeComponent#requestEdge(EdgeRequest)
 */
public class EdgeRequest extends Request<EdgeComponent> {

	/**
	 * <p>
	 * doRequestEdge.
	 * </p>
	 *
	 * @param edgeId
	 *            a {@link java.lang.String} object.
	 * @param requestTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.requests.PnRequestTarget}
	 *            object.
	 * @return a {@link de.markusrother.pned.gui.components.EdgeComponent}
	 *         object.
	 */
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

	/**
	 * <p>
	 * Constructor for EdgeRequest.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param edgeId
	 *            a {@link java.lang.String} object.
	 */
	public EdgeRequest(final Object source, final String edgeId) {
		super(source);
		this.edgeId = edgeId;
	}

	/**
	 * <p>
	 * createWorker.
	 * </p>
	 *
	 * @param l
	 *            a
	 *            {@link de.markusrother.pned.gui.control.requests.EdgeRequestListener}
	 *            object.
	 * @return a {@link javax.swing.SwingWorker} object.
	 */
	public SwingWorker<EdgeComponent, Object> createWorker(final EdgeRequestListener l) {
		return new EdgeRequestWorker(this, l);
	}

	/**
	 * <p>
	 * Getter for the field <code>edgeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getEdgeId() {
		return edgeId;
	}

}
