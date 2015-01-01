package de.markusrother.pned.gui.events;

import javax.swing.SwingWorker;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.layout.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.requests.NodeRequest;

/**
 * FIXME - Create copy constructor from EventBus
 *
 * @author Markus Rother
 * @version 1.0
 */
public class GuiEventBus extends EventBus
	implements
		GuiEventTarget {

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		for (final PetriNetListener l : getListeners(PetriNetListener.class)) {
			l.createPetriNet(cmd);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		for (final NodeListener l : getListeners(NodeListener.class)) {
			l.setCurrentNodeType(cmd);
		}
	}

	/** {@inheritDoc} */
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

	/**
	 * <p>
	 * fireNodeSelectionEvent.
	 * </p>
	 *
	 * @param e
	 *            a {@link de.markusrother.pned.gui.events.NodeSelectionEvent}
	 *            object.
	 */
	public void fireNodeSelectionEvent(final NodeSelectionEvent e) {
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
	}

	/**
	 * <p>
	 * fireEdgeEditEvent.
	 * </p>
	 *
	 * @param e
	 *            a {@link de.markusrother.pned.gui.events.EdgeEditEvent}
	 *            object.
	 */
	public void fireEdgeEditEvent(final EdgeEditEvent e) {
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

	/** {@inheritDoc} */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
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
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
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
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
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
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
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
