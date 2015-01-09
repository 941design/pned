package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.LabelEditListener;
import de.markusrother.pned.control.commands.MarkingEditCommand;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOListener;
import de.markusrother.pned.control.commands.PlaceCommandListener;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionActivationListener;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.commands.TransitionListener;
import de.markusrother.pned.control.events.MarkingChangeEvent;
import de.markusrother.pned.control.events.PlaceEventListener;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.requests.IdRequest;
import de.markusrother.pned.control.requests.IdRequestListener;
import de.markusrother.pned.gui.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.commands.EdgeLayoutListener;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.commands.GuiCommandTarget;
import de.markusrother.pned.gui.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.commands.MarkingLayoutListener;
import de.markusrother.pned.gui.commands.NodeListener;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.PetriNetListener;
import de.markusrother.pned.gui.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.commands.PlaceLayoutListener;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.commands.TransitionLayoutListener;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.EdgeEditListener;
import de.markusrother.pned.gui.events.GuiEventTarget;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.NodeSelectionListener;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.requests.GuiRequestTarget;
import de.markusrother.pned.gui.requests.NodeRequest;
import de.markusrother.pned.gui.requests.NodeRequestListener;

/**
 * <p>
 * Abstract PetriNetGuiEventAdapter class.
 * </p>
 *
 * FIXME - Name does not fit!
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class PetriNetGuiEventAdapter
	implements
		GuiCommandTarget,
		GuiEventTarget,
		GuiRequestTarget {

	/**
	 * The event target which is listened to.
	 */
	protected PnEventBus eventBus;

	/**
	 * <p>
	 * Setter for the field <code>eventBus</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.PnEventBus} to
	 *            which resulting events are posted to and to which is listened
	 *            to for state changes.
	 */
	public void setEventBus(final PnEventBus eventBus) {
		if (this.eventBus != null) {
			suspendListeners();
		}
		this.eventBus = eventBus;
		installListeners();
	}

	/**
	 * <p>
	 * installListeners.
	 * </p>
	 */
	protected void installListeners() {
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(NodeListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceCommandListener.class, this);
		eventBus.addListener(PlaceEventListener.class, this);
		eventBus.addListener(EdgeEditListener.class, this);
		eventBus.addListener(NodeRequestListener.class, this);
		eventBus.addListener(PlaceLayoutListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(TransitionListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
		eventBus.addListener(TransitionLayoutListener.class, this);
		eventBus.addListener(EdgeLayoutListener.class, this);
		eventBus.addListener(MarkingLayoutListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
		eventBus.addListener(IdRequestListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	/**
	 * <p>
	 * suspendListeners.
	 * </p>
	 */
	protected void suspendListeners() {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent event) {
		process(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeMultiSelectionEvent event) {
		process(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void componentEntered(final EdgeEditEvent e) {
		process(e);
	}

	/** {@inheritDoc} */
	@Override
	public void componentExited(final EdgeEditEvent e) {
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

	/** {@inheritDoc} */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void requestNode(final NodeRequest req) {
		process(req);
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
	public void setMarking(final MarkingChangeEvent evt) {
		process(evt);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final MarkingEditCommand evt) {
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
