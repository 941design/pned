package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.events.MarkingEventObject;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.listeners.CommandTarget;
import de.markusrother.pned.control.listeners.EdgeCreationListener;
import de.markusrother.pned.control.listeners.EventTarget;
import de.markusrother.pned.control.listeners.IdRequestListener;
import de.markusrother.pned.control.listeners.LabelEditListener;
import de.markusrother.pned.control.listeners.NodeCreationListener;
import de.markusrother.pned.control.listeners.NodeMotionListener;
import de.markusrother.pned.control.listeners.PetriNetIOListener;
import de.markusrother.pned.control.listeners.PlaceListener;
import de.markusrother.pned.control.listeners.RequestTarget;
import de.markusrother.pned.control.listeners.TransitionActivationListener;
import de.markusrother.pned.control.listeners.TransitionListener;
import de.markusrother.pned.control.requests.IdRequest;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;

/**
 * <p>
 * Abstract PetriNetEventAdapter class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PetriNetEventAdapter
	implements
		CommandTarget,
		EventTarget,
		RequestTarget {

	/**
	 * <p>
	 * setEventBus.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public void setEventBus(final EventBus eventBus) {
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(TransitionListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdge(final EdgeRemoveCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final MarkingEventObject evt) {
		process(evt);
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void requestId(final IdRequest req) {
		process(req);
	}

	/** {@inheritDoc} */
	@Override
	public void fireTransition(final TransitionExecutionCommand cmd) {
		process(cmd);
	}

	/**
	 * <p>
	 * process.
	 * </p>
	 *
	 * @param e
	 *            a {@link java.util.EventObject} object.
	 */
	protected abstract void process(final EventObject e);

}
