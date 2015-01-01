package de.markusrother.pned.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.markusrother.pned.core.events.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.IdRequest;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

/**
 * TODO - suspend/enable on selection event!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeCreator extends MouseAdapter
	implements
		NodeListener {

	private NodeCreationMode mode;
	private final EventBus eventBus;

	/**
	 * <p>Constructor for NodeCreator.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.core.events.EventBus} object.
	 */
	public NodeCreator(final EventBus eventBus) {
		this.eventBus = eventBus;
		this.mode = NodeCreationMode.defaultCreationMode;
		eventBus.addListener(NodeListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(final MouseEvent e) {
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
		final String nodeId = requestId();
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

	/**
	 * <p>requestId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	private String requestId() {
		final IdRequest req = new IdRequest(this);
		eventBus.requestId(req);
		return req.get();
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		this.mode = cmd.getMode();
	}

}
