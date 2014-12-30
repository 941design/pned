package de.markusrother.pned.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.EventListenerList;

import de.markusrother.pned.commands.EdgeLayoutCommand;
import de.markusrother.pned.commands.MarkingLayoutCommand;
import de.markusrother.pned.commands.PetriNetEditCommand;
import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.commands.PlaceLayoutCommand;
import de.markusrother.pned.commands.TransitionLayoutCommand;
import de.markusrother.pned.commands.listeners.EdgeLayoutListener;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;
import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.commands.listeners.PetriNetListener;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.commands.listeners.TransitionActivationListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;
import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.events.TransitionActivationEvent;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeRequest;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.LabelEditListener;
import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

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
		PetriNetListener,
		PetriNetCommandSource,
		PetriNetIOListener,
		TransitionActivationListener,
		AWTEventListener,
		EventTarget,
		NodeListener,
		NodeRequestListener,
		NodeMotionListener,
		NodeCreationListener,
		NodeRemovalListener,
		PlaceEditListener,
		PlaceLayoutListener,
		TransitionLayoutListener,
		MarkingLayoutListener,
		LabelEditListener,
		EdgeLayoutListener {

	private final EventListenerList listeners = new EventListenerList();

	@Override
	public <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return listeners.getListeners(clazz);
	}

	@Override
	public <T extends EventListener> void addListener(final Class<T> clazz, final T l) {
		// TODO - maintain a map which only dispatches events to given listener?
		// (Would be unconventional)
		listeners.add(clazz, l);
	}

	@Override
	public <T extends EventListener> void removeListener(final Class<T> clazz, final T l) {
		listeners.remove(clazz, l);
	}

	@Override
	public void disposePetriNet(final PetriNetEditCommand cmd) {
		for (final PetriNetListener l : getListeners(PetriNetListener.class)) {
			l.disposePetriNet(cmd);
		}
	}

	@Override
	public void importPnml(final PetriNetIOCommand cmd) throws IOException {
		for (final PetriNetIOListener l : getListeners(PetriNetIOListener.class)) {
			l.importPnml(cmd);
		}
	}

	@Override
	public void exportPnml(final PetriNetIOCommand cmd) throws IOException {
		for (final PetriNetIOListener l : getListeners(PetriNetIOListener.class)) {
			l.exportPnml(cmd);
		}
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand e) {
		eventDispatched(e);
	}

	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void setMarking(final PlaceEditEvent cmd) {
		eventDispatched(cmd);
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

	@Override
	public void nodeMoved(final NodeMovedEvent e) {
		eventDispatched(e);
	}

	public void fireEdgeEditEvent(final EdgeEditEvent e) {
		eventDispatched(e);
	}

	@Override
	public void setLabel(final LabelEditEvent e) {
		eventDispatched(e);
	}

	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
		eventDispatched(cmd);
	}

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		for (final TransitionActivationListener l : getListeners(TransitionActivationListener.class)) {
			l.transitionActivated(e);
		}
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		for (final TransitionActivationListener l : getListeners(TransitionActivationListener.class)) {
			l.transitionDeactivated(e);
		}
	}

	@Override
	public void requestNode(final NodeRequest req) {
		for (final NodeRequestListener l : getListeners(NodeRequestListener.class)) {
			final SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() {
					l.requestNode(req);
					return null;
				}
			};
			worker.execute();
		}
	}

	public EventBus() {
		// TODO - We could reduce overhead if we could inject a custom mask
		// instead of using ACTION_PERFORMED for all our custom events:
		Toolkit.getDefaultToolkit().addAWTEventListener(this, ActionEvent.ACTION_PERFORMED);
	}

	@Override
	public void eventDispatched(final AWTEvent event) {
		if (!SwingUtilities.isEventDispatchThread()) {
			System.err.println("Not on EDT: " + event);
			try {
				// FIXME - Create an EventTarget that puts events on EDT!
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						eventDispatched(event);
					}
				});
				return;
			} catch (InvocationTargetException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (event instanceof SetNodeTypeCommand) {
			final SetNodeTypeCommand cmd = (SetNodeTypeCommand) event;
			for (final NodeListener l : getListeners(NodeListener.class)) {
				l.setCurrentNodeType(cmd);
			}
		} else if (event instanceof PlaceCreationCommand) {
			final PlaceCreationCommand cmd = (PlaceCreationCommand) event;
			for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
				l.createPlace(cmd);
			}
		} else if (event instanceof TransitionCreationCommand) {
			final TransitionCreationCommand cmd = (TransitionCreationCommand) event;
			for (final NodeCreationListener l : getListeners(NodeCreationListener.class)) {
				l.createTransition(cmd);
			}
		} else if (event instanceof EdgeCreationCommand) {
			final EdgeCreationCommand cmd = (EdgeCreationCommand) event;
			for (final EdgeCreationListener l : getListeners(EdgeCreationListener.class)) {
				l.createEdge(cmd);
			}
		} else if (event instanceof NodeRemovalEvent) {
			final NodeRemovalEvent e = (NodeRemovalEvent) event;
			for (final NodeRemovalListener l : getListeners(NodeRemovalListener.class)) {
				l.nodeRemoved(e);
			}
		} else if (event instanceof RemoveSelectedNodesEvent) {
			final RemoveSelectedNodesEvent e = (RemoveSelectedNodesEvent) event;
			for (final NodeRemovalListener l : getListeners(NodeRemovalListener.class)) {
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
		} else if (event instanceof PlaceEditEvent) {
			final PlaceEditEvent e = (PlaceEditEvent) event;
			for (final PlaceEditListener l : getListeners(PlaceEditListener.class)) {
				l.setMarking(e);
			}
		} else if (event instanceof LabelEditEvent) {
			final LabelEditEvent e = (LabelEditEvent) event;
			for (final LabelEditListener l : getListeners(LabelEditListener.class)) {
				l.setLabel(e);
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
		} else if (event instanceof PlaceLayoutCommand) {
			final PlaceLayoutCommand cmd = (PlaceLayoutCommand) event;
			for (final PlaceLayoutListener l : getListeners(PlaceLayoutListener.class)) {
				switch (cmd.getType()) {
				case SIZE:
					l.setSize(cmd);
					break;
				case SELECTION_COLOR:
				case DEFAULT_COLOR:
				case DEFAULT_BORDER_COLOR:
				case SELECTION_BORDER_COLOR:
				case HOVER_COLOR:
				case HOVER_BORDER_COLOR:
				default:
					throw new IllegalStateException();
				}
			}
		} else if (event instanceof TransitionLayoutCommand) {
			final TransitionLayoutCommand cmd = (TransitionLayoutCommand) event;
			for (final TransitionLayoutListener l : getListeners(TransitionLayoutListener.class)) {
				switch (cmd.getType()) {
				case SIZE:
					l.setSize(cmd);
					break;
				case SELECTION_COLOR:
				case DEFAULT_COLOR:
				case DEFAULT_BORDER_COLOR:
				case SELECTION_BORDER_COLOR:
				case HOVER_COLOR:
				case HOVER_BORDER_COLOR:
				default:
					throw new IllegalStateException();
				}
			}
		} else if (event instanceof MarkingLayoutCommand) {
			final MarkingLayoutCommand cmd = (MarkingLayoutCommand) event;
			for (final MarkingLayoutListener l : getListeners(MarkingLayoutListener.class)) {
				switch (cmd.getType()) {
				case SIZE:
					l.setSize(cmd);
					break;
				case SELECTION_COLOR:
				case DEFAULT_COLOR:
				case DEFAULT_BORDER_COLOR:
				case SELECTION_BORDER_COLOR:
				case HOVER_COLOR:
				case HOVER_BORDER_COLOR:
				default:
					throw new IllegalStateException();
				}
			}
		} else if (event instanceof EdgeLayoutCommand) {
			final EdgeLayoutCommand cmd = (EdgeLayoutCommand) event;
			for (final EdgeLayoutListener l : getListeners(EdgeLayoutListener.class)) {
				switch (cmd.getType()) {
				case SIZE:
					l.setSize(cmd);
					break;
				case SELECTION_COLOR:
				case DEFAULT_COLOR:
				case DEFAULT_BORDER_COLOR:
				case SELECTION_BORDER_COLOR:
				case HOVER_COLOR:
				case HOVER_BORDER_COLOR:
				default:
					throw new IllegalStateException();
				}
			}
		}
	}

}
