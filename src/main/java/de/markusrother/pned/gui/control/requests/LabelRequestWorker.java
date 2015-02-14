package de.markusrother.pned.gui.control.requests;

import javax.swing.SwingWorker;

import de.markusrother.pned.gui.components.LabelComponent;

public class LabelRequestWorker extends SwingWorker<LabelComponent, Object> {

	private final LabelRequest request;
	private final LabelRequestListener listener;

	public LabelRequestWorker(final LabelRequest request, final LabelRequestListener listener) {
		this.request = request;
		this.listener = listener;
	}

	@Override
	protected LabelComponent doInBackground() throws Exception {
		listener.requestLabel(request);
		return request.get();
	}

}
