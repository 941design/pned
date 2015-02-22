package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.CommandTarget;
import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.LabelEditListener;
import de.markusrother.pned.control.commands.MarkingEditCommand;
import de.markusrother.pned.control.commands.MarkingEditListener;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOListener;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.control.commands.TransitionExecutionCommand;
import de.markusrother.pned.control.commands.TransitionListener;
import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;

/**
 * <p>Abstract CommandAdapter class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class CommandAdapter
	implements
		CommandTarget {

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
		eventBus.addListener(MarkingEditListener.class, this);
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(TransitionListener.class, this);
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
	public void setMarking(final MarkingEditCommand cmd) {
		process(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand e) {
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
