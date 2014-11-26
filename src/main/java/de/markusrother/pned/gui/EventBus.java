package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

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
 * 
 * TODO - We could distinguish Two sources GUI and MODEL. GUI could be anything
 * static that can be compared against, to distinguish where a creation event
 * came from.
 */
public class EventBus
	implements
		AWTEventListener,
		NodeListener {

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

	public void addEdgeEditListener(final EdgeEditListener l) {
		listeners.add(EdgeEditListener.class, l);
	}

	public void removeEdgeEditListener(final EdgeEditListener l) {
		listeners.remove(EdgeEditListener.class, l);
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand e) {
		eventDispatched(e);
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// TODO - which idiom?
		// TODO - should be defined in an interface
		eventDispatched(e);
	}

	@Override
	public void createPlace(final PlaceCreationRequest e) {
		// TODO - This is a gui-only event
		eventDispatched(e);
	}

	@Override
	public void createTransition(final TransitionCreationRequest e) {
		// TODO - This is a gui-only event
		eventDispatched(e);
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		eventDispatched(e);
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
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

	public void fireEdgeEditEvent(final EdgeEditEvent e) {
		eventDispatched(e);
	}

	public EventBus() {
		// TODO - We could reduce overhead if we could inject a custom mask
		// instead of using ACTION_PERFORMED for all our custom events:
		Toolkit.getDefaultToolkit().addAWTEventListener(this, ActionEvent.ACTION_PERFORMED);
	}

	@Override
	public void eventDispatched(final AWTEvent event) {
		if (event instanceof SetNodeTypeCommand) {
			final SetNodeTypeCommand cmd = (SetNodeTypeCommand) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.setCurrentNodeType(cmd);
			}
		} else if (event instanceof NodeCreationEvent) {
			final NodeCreationEvent e = (NodeCreationEvent) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.nodeCreated(e);
			}
		} else if (event instanceof PlaceCreationRequest) {
			final PlaceCreationRequest e = (PlaceCreationRequest) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.createPlace(e);
			}
		} else if (event instanceof TransitionCreationRequest) {
			final TransitionCreationRequest e = (TransitionCreationRequest) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.createTransition(e);
			}
		} else if (event instanceof NodeRemovalEvent) {
			final NodeRemovalEvent e = (NodeRemovalEvent) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.nodeRemoved(e);
			}
		} else if (event instanceof RemoveSelectedNodesEvent) {
			final RemoveSelectedNodesEvent e = (RemoveSelectedNodesEvent) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.removeSelectedNodes(e);
			}
		} else if (event instanceof NodeSelectionEvent) {
			final NodeSelectionEvent e = (NodeSelectionEvent) event;
			for (final NodeSelectionListener l : getListeners(NodeSelectionListener.class)) {
				switch (e.getType()) {
				case SELECT:
					l.nodesSelected(e);
					break;
				case DESELECT:
					l.nodesUnselected(e);
					break;
				case FINISH:
					l.nodeSelectionFinished(e);
					break;
				case CANCEL:
					l.nodeSelectionCancelled(e);
					break;
				default:
					throw new IllegalStateException();
				}
			}
		} else if (event instanceof NodeMovedEvent) {
			final NodeMovedEvent e = (NodeMovedEvent) event;
			for (final NodeMotionListener l : getListeners(NodeMotionListener.class)) {
				l.nodeMoved(e);
			}
		} else if (event instanceof EdgeEditEvent) {
			final EdgeEditEvent e = (EdgeEditEvent) event;
			for (final EdgeEditListener l : getListeners(EdgeEditListener.class)) {
				switch (e.getType()) {
				case COMPONENT_ENTERED:
					l.targetComponentEntered(e);
					break;
				case COMPONENT_EXITED:
					l.targetComponentExited(e);
					break;
				case EDGE_CHANGED:
					l.edgeMoved(e);
					break;
				case EDGE_CANCELLED:
					l.edgeCancelled(e);
					break;
				case EDGE_FINISHED:
					l.edgeFinished(e);
					break;
				case EDGE_STARTED:
					l.edgeStarted(e);
					break;
				default:
					throw new IllegalStateException();
				}
			}
		}
	}
}
