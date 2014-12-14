package de.markusrother.pned.gui.listeners;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

/**
 * TODO - suspend/enable on selection event!
 *
 */
public class NodeCreator extends MouseAdapter
	implements
		NodeListener {

	private NodeCreationMode mode;

	public NodeCreator() {
		this.mode = NodeCreationMode.defaultCreationMode;
		eventBus.addListener(NodeListener.class, this);
	}

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
		switch (mode) {
		case PLACE:
			// FIXME - Must create ID!
			eventBus.createPlace(new PlaceCreationCommand(this, e.getPoint()));
			break;
		case TRANSITION:
			// FIXME - Must create ID!
			eventBus.createTransition(new TransitionCreationCommand(this, e.getPoint()));
			break;
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		this.mode = cmd.getMode();
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

}
