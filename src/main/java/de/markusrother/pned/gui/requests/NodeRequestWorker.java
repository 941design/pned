package de.markusrother.pned.gui.requests;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.pned.gui.listeners.NodeRequestListener;

class NodeRequestWorker extends SwingWorker<AbstractNode, Object> {

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
