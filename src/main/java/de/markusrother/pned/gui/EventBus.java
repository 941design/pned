package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * Should be retrieved from frame/application (after the EDT is created).
 * 
 * Rename to PnedAwtEventMulticaster as opposed to the
 * PnedModelEventMulticaster. Maybe we can get rid of the swing objects here,
 * already.
 * 
 * TODO - create an PnedEvent superclass. (for what?)
 * 
 * TODO - which idiom?
 */
public class EventBus implements AWTEventListener, NodeListener {

	private final EventListenerList listeners = new EventListenerList();

	private <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return listeners.getListeners(clazz);
	}

	public void addNodeListener(final NodeListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeListener.class, l);
	}

	public void removeNodeListener(final NodeListener l) {
		listeners.remove(NodeListener.class, l);
	}

	public void addNodeSelectionListener(final NodeSelectionListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeSelectionListener.class, l);
	}

	public void removeNodeSelectionListener(final NodeSelectionListener l) {
		listeners.remove(NodeSelectionListener.class, l);
	}

	public void addNodeMotionListener(final NodeMotionListener l) {
		// TODO - should be defined in an interface
		listeners.add(NodeMotionListener.class, l);
	}

	public void removeNodeMotionListener(final NodeMotionListener l) {
		listeners.remove(NodeMotionListener.class, l);
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// TODO - which idiom?
		// TODO - should be defined in an interface
		eventDispatched(e);
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
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
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.nodeCreated(e);
			}
		} else if (event instanceof NodeRemovalEvent) {
			final NodeRemovalEvent e = (NodeRemovalEvent) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.nodeRemoved(e);
			}
		} else if (event instanceof NodeSelectionEvent) {
			final NodeSelectionEvent e = (NodeSelectionEvent) event;
			switch (e.getType()) {
			case SELECT:
				for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
					l.nodesSelected(e);
				}
				break;
			case DESELECT:
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
