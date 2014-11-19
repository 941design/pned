package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;

import javax.swing.event.EventListenerList;

/**
 * should be retrieved from frame/application (after the EDT is created).
 */
public class EventBus implements AWTEventListener {

	private final EventListenerList listeners = new EventListenerList();

	public void addNodeSelectionListener(final NodeSelectionListener l) {
		listeners.add(NodeSelectionListener.class, l);
	}

	public void fireNodeSelectionEvent(final NodeSelectionEvent e) {
		eventDispatched(e);
	}

	public EventBus() {
		// TODO - We could reduce overhead if we could inject a custom mask
		// instead of using ACTION_PERFORMED for all our custom events:
		Toolkit.getDefaultToolkit().addAWTEventListener(this, ActionEvent.ACTION_PERFORMED);
	}

	@Override
	public void eventDispatched(final AWTEvent event) {
		if (event instanceof NodeSelectionEvent) {
			final NodeSelectionEvent nodeSelectionEvent = (NodeSelectionEvent) event;
			switch (nodeSelectionEvent.getType()) {
			case SELECTED:
				for (final NodeSelectionListener l : listeners.getListeners(NodeSelectionListener.class)) {
					l.nodesSelected(nodeSelectionEvent);
				}
				break;
			case UNSELECTED:
				for (final NodeSelectionListener l : listeners.getListeners(NodeSelectionListener.class)) {
					l.nodesUnselected(nodeSelectionEvent);
				}
				break;
			default:
				throw new IllegalStateException();
			}
		}
	}
}
