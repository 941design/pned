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
 * <p>
 * Creator of {@link de.markusrother.pned.gui.components.AbstractNode}s.
 * </p>
 * 
 * TODO - Create a stateful NodeCreationAction and receive it as constructor
 * parameter.
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
