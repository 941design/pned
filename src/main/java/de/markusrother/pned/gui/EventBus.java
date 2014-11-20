package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * should be retrieved from frame/application (after the EDT is created).
 */
public class EventBus implements AWTEventListener {

	private final EventListenerList listeners = new EventListenerList();

	private <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return listeners.getListeners(clazz);
	}

	public void addNodeCreationListener(final NodeCreationListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeCreationListener.class, l);
	}

	public void addNodeSelectionListener(final NodeSelectionListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeSelectionListener.class, l);
	}

	public void addNodeMotionListener(final NodeMotionListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeMotionListener.class, l);
	}

	public void fireNodeCreationEvent(final NodeCreationEvent e) {
		// TODO - should be defined in an interface
		eventDispatched(e);
	}

	public void fireNodeSelectionEvent(final NodeSelectionEvent e) {
		// TODO - should be defined in an interface
		eventDispatched(e);
	}

	public void fireNodeMovedEvent(final NodeMovedEvent e) {
		// TODO - should be defined in an interface
		eventDispatched(e);
	}

	public EventBus() {
		// TODO - We could reduce overhead if we could inject a custom mask
		// instead of using ACTION_PERFORMED for all our custom events:
		Toolkit.getDefaultToolkit().addAWTEventListener(this, ActionEvent.ACTION_PERFORMED);
	}

	@Override
	public void eventDispatched(final AWTEvent event) {
		if (event instanceof NodeCreationEvent) {
			final NodeCreationEvent e = (NodeCreationEvent) event;
			for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
				l.nodeCreated(e);
			}
		}
		if (event instanceof NodeSelectionEvent) {
			final NodeSelectionEvent e = (NodeSelectionEvent) event;
			switch (e.getType()) {
			case SELECTED:
				for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
					l.nodesSelected(e);
				}
				break;
			case UNSELECTED:
				for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
					l.nodesUnselected(e);
				}
				break;
			default:
				throw new IllegalStateException();
			}
		} else if (event instanceof NodeMovedEvent) {
			final NodeMovedEvent e = (NodeMovedEvent) event;
			for (final NodeMotionListener l : getListeners(NodeMotionListener.class)) {
				l.nodeMoved(e);
			}
		}
	}
}
