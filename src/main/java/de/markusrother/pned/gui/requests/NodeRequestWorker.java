package de.markusrother.pned.gui.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.AbstractNode;

/**
 * <p>
 * NodeRequestWorker class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
class NodeRequestWorker extends SwingWorker<AbstractNode, Object> {

	protected final NodeRequest request;
	protected final NodeRequestListener listener;

	/**
	 * <p>
	 * Constructor for NodeRequestWorker.
	 * </p>
	 *
	 * @param request
	 *            a {@link de.markusrother.pned.gui.requests.NodeRequest}
	 *            object.
	 * @param listener
	 *            a
	 *            {@link de.markusrother.pned.gui.requests.NodeRequestListener}
	 *            object.
	 */
	public NodeRequestWorker(final NodeRequest request, final NodeRequestListener listener) {
		this.request = request;
		this.listener = listener;
	}

	/** {@inheritDoc} */
	@Override
	protected AbstractNode doInBackground() throws TimeoutException {
		// NOTE - This method is called from a thread-pool!
		listener.requestNode(request);
		// TODO - Create a synchronized counter to verify that no threads hang.
		return request.get();
	}

	/** {@inheritDoc} */
	@Override
	protected void done() {
		// NOTE - This is called from the EDT!
		// IGNORE
	}

}
