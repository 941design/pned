package de.markusrother.pned.gui.control.requests;

import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.EdgeComponent;

public class EdgeRequestWorker extends SwingWorker<EdgeComponent, Object> {

	private final EdgeRequest request;
	private final EdgeRequestListener listener;

	public EdgeRequestWorker(final EdgeRequest request, final EdgeRequestListener listener) {
		this.request = request;
		this.listener = listener;
	}

	/** {@inheritDoc} */
	@Override
	protected EdgeComponent doInBackground() throws TimeoutException {
		listener.requestEdge(request);
		return request.get();
	}

}
