package de.markusrother.pned.core.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.listeners.IdRequestListener;

class IdRequestWorker extends SwingWorker<String, Object> {

	private final IdRequest request;
	private final IdRequestListener listener;

	/**
	 * <p>Constructor for IdRequestWorker.</p>
	 *
	 * @param request a {@link de.markusrother.pned.core.requests.IdRequest} object.
	 * @param listener a {@link de.markusrother.pned.core.listeners.IdRequestListener} object.
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
