package de.markusrother.pned.util;

import java.util.EventObject;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.commands.NodeMovedEvent;
import de.markusrother.pned.core.commands.NodeRemovalEvent;
import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditEvent;
import de.markusrother.pned.core.commands.RemoveSelectedNodesEvent;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.core.listeners.NodeRemovalListener;
import de.markusrother.pned.core.listeners.PlaceEditListener;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

/**
 * <p>
 * PetriNetEventLogger class.
 * </p>
 *
 * FIXME - use compound interfaces
 * 
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetEventLogger
	implements
		NodeListener,
		NodeCreationListener,
		NodeRemovalListener,
		NodeMotionListener,
		NodeSelectionListener,
		PlaceEditListener,
		EdgeCreationListener,
		EdgeEditListener {

	/**
	 * <p>
	 * instantiate.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public static void instantiate(final EventBus eventBus) {
		final PetriNetEventLogger mock = new PetriNetEventLogger();
		eventBus.addListener(NodeListener.class, mock);
		eventBus.addListener(NodeRemovalListener.class, mock);
		eventBus.addListener(NodeCreationListener.class, mock);
		eventBus.addListener(NodeMotionListener.class, mock);
		eventBus.addListener(NodeSelectionListener.class, mock);
		eventBus.addListener(PlaceEditListener.class, mock);
		eventBus.addListener(EdgeCreationListener.class, mock);
		eventBus.addListener(EdgeEditListener.class, mock);
	}

	/**
	 * <p>
	 * log.
	 * </p>
	 *
	 * @param event
	 *            a {@link java.util.EventObject} object.
	 */
	private void log(final EventObject event) {
		System.out.println(event.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Constructor for PetriNetEventLogger.
	 * </p>
	 */
	private PetriNetEventLogger() {
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		log(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		log(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		log(event);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		// IGNORE - Too many events
	}

	/** {@inheritDoc} */
	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMovedEvent e) {
		log(e);
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		log(cmd);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEditEvent cmd) {
		log(cmd);
	}

}
