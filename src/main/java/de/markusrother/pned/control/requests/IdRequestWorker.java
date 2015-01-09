package de.markusrother.pned.control.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.control.listeners.IdRequestListener;

/**
 * <p>IdRequestWorker class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
class IdRequestWorker extends SwingWorker<String, Object> {

	private final IdRequest request;
	private final IdRequestListener listener;

	/**
	 * <p>Constructor for IdRequestWorker.</p>
	 *
	 * @param request a {@link de.markusrother.pned.control.requests.IdRequest} object.
	 * @param listener a {@link de.markusrother.pned.control.listeners.IdRequestListener} object.
	 */
	public IdRequestWorker(final IdRequest request, final IdRequestListener listener) {
		this.request = request;
		this.listener = listener;
	}

	/** {@inheritDoc} */
	@Override
	protected String doInBackground() throws TimeoutException {
		listener.requestId(request);
		return request.get();
	}

	/** {@inheritDoc} */
	@Override
	protected void done() {
		try {
			get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			// TODO - use custom exception
			throw new RuntimeException("TODO");
		}
	}

}
