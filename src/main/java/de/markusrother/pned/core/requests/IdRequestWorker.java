package de.markusrother.pned.core.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.listeners.IdRequestListener;

class IdRequestWorker extends SwingWorker<String, Object> {

	private final IdRequest request;
	private final IdRequestListener listener;

	public IdRequestWorker(final IdRequest request, final IdRequestListener listener) {
		this.request = request;
		this.listener = listener;
	}

	@Override
	protected String doInBackground() throws TimeoutException {
		listener.requestId(request);
		return request.get();
	}

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
