package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.commands.TransitionExecutionCommand;
import de.markusrother.pned.core.events.PlaceEventObject;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.requests.IdRequest;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.layout.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;
import de.markusrother.pned.gui.listeners.GuiEventTarget;
import de.markusrother.pned.gui.listeners.GuiRequestTarget;
import de.markusrother.pned.gui.requests.NodeRequest;

/**
 * <p>AbstractGuiAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractGuiAction extends AbstractAction
	implements
		GuiCommandTarget,
		GuiEventTarget,
		GuiRequestTarget {

	protected final Object source;
	protected final GuiState state;

	/**
	 * <p>Constructor for AbstractGuiAction.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param state a {@link de.markusrother.pned.gui.actions.GuiState} object.
	 */
	public AbstractGuiAction(final String label, final Object source, final GuiState state) {
		super(label);
		this.source = source;
		this.state = state;
	}

	/**
	 * <p>getEventBus.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	protected GuiEventBus getEventBus() {
		return state.getEventBus();
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) throws IOException {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) throws IOException {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdge(final EdgeRemoveCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEventObject evt) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void fireTransition(final TransitionExecutionCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final EdgeLayoutCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeMultiSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void componentEntered(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void componentExited(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void requestId(final IdRequest req) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void requestNode(final NodeRequest req) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// IGNORE
	}

}
