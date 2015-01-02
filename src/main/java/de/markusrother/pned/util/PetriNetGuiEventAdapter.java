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
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.layout.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;
import de.markusrother.pned.gui.listeners.GuiEventTarget;
import de.markusrother.pned.gui.listeners.GuiRequestTarget;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.requests.NodeRequest;

public abstract class PetriNetGuiEventAdapter
	implements
		GuiCommandTarget,
		GuiEventTarget,
		GuiRequestTarget {

	public void setEventBus(final GuiEventBus eventBus) {
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(NodeListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceEditListener.class, this);
		eventBus.addListener(EdgeEditListener.class, this);
		eventBus.addListener(NodeRequestListener.class, this);
		eventBus.addListener(PlaceLayoutListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
		eventBus.addListener(TransitionLayoutListener.class, this);
		eventBus.addListener(EdgeLayoutListener.class, this);
		eventBus.addListener(MarkingLayoutListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		process(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		process(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		process(e);
	}

	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		process(cmd);
	}

	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		process(cmd);
	}

	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
		process(cmd);
	}

	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		process(cmd);
	}

	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		process(cmd);
	}

	@Override
	public void requestNode(final NodeRequest req) {
		process(req);
	}

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

	@Override
	public void requestId(final IdRequest req) {
		process(req);
	}

	protected abstract void process(final EventObject e);

}
