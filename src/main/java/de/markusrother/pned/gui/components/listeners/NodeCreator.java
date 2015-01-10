package de.markusrother.pned.gui.components.listeners;

import java.awt.event.MouseEvent;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.gui.control.PnState.NodeCreationMode;
import de.markusrother.pned.gui.control.commands.NodeListener;
import de.markusrother.pned.gui.control.commands.SetNodeTypeCommand;
import de.markusrother.swing.LeftClickListener;

/**
 * TODO - suspend/enable on selection event!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeCreator extends LeftClickListener
	implements
		NodeListener {

	private NodeCreationMode mode;
	private final EventBus eventBus;

	/**
	 * <p>
	 * Constructor for NodeCreator.
	 * </p>
	 *
	 * TODO - take NodeCreationAction as param.
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public NodeCreator(final EventBus eventBus) {
		this.eventBus = eventBus;
		this.mode = NodeCreationMode.defaultCreationMode;
		eventBus.addListener(NodeListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedLeft(final MouseEvent e) {
		// TODO - this could go through the event bus.
		// Should the node creation listener itself have a state and listen
		// to events? Or should components toggle listeners depending on
		// state? That would result in a mapping of enum sets of states to
		// listener configurations. Those configurations can be verified by
		// partitioning the power set and mapping the partitions to listener
		// configurations. However, as power sets are too large we would
		// have to compute the conditions manually as if-else. We could use
		// the results to create documentation, though, and to test! Such
		// that: For a given configuration of states we expect a list of
		// activated listeners.
		final String nodeId = eventBus.requestId();
		switch (mode) {
		case PLACE:
			eventBus.createPlace(new PlaceCreationCommand(this, nodeId, e.getPoint()));
			break;
		case TRANSITION:
			eventBus.createTransition(new TransitionCreationCommand(this, nodeId, e.getPoint()));
			break;
		default:
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		this.mode = cmd.getMode();
	}

}
