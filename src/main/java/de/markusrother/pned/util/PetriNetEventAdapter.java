package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.control.CommandTarget;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.control.EventTarget;
import de.markusrother.pned.core.control.RequestTarget;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.IdRequestListener;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.core.listeners.TransitionActivationListener;
import de.markusrother.pned.core.requests.IdRequest;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;

public abstract class PetriNetEventAdapter
	implements
		CommandTarget,
		EventTarget,
		RequestTarget {

	public void setEventBus(final EventBus eventBus) {
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceEditListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		process(cmd);
	}

	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		process(cmd);
	}

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		process(cmd);
	}

	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		process(e);
	}

	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		process(e);
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		process(e);
	}

	@Override
	public void setMarking(final PlaceEditCommand cmd) {
		process(cmd);
	}

	@Override
	public void setLabel(final LabelEditCommand e) {
		process(e);
	}

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		process(e);
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		process(e);
	}

	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		process(cmd);
	}

	protected abstract void process(final EventObject e);

	@Override
	public void requestId(final IdRequest req) {
		// TODO
		throw new RuntimeException("TODO");
	}

}
