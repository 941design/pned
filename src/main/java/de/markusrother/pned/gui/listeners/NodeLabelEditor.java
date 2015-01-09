package de.markusrother.pned.gui.listeners;

import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.gui.components.NodeLabel;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.swing.CheckedTextField;
import de.markusrother.swing.RightClickTextFieldEdit;

/**
 * <p>
 * NodeLabelEditor class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabelEditor extends RightClickTextFieldEdit<NodeLabel>
	implements
		NodeCreationListener,
		NodeSelectionListener {

	/** Constant <code>labelPattern</code> */
	public final static Pattern labelPattern = Pattern.compile("[\\w\\s\\d]*");

	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * Constructor for NodeLabelEditor.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	public NodeLabelEditor(final GuiEventBus eventBus) {
		super(NodeLabel.class, labelPattern);

		this.eventBus = eventBus;

		// FIXME - dispose!
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getInitialText(final NodeLabel nodeLabel) {
		return nodeLabel.getText();
	}

	/** {@inheritDoc} */
	@Override
	public void startEdit(final NodeLabel nodeLabel, final MouseEvent evt) {
		nodeLabel.setVisible(false);
	}

	/** {@inheritDoc} */
	@Override
	public void cancelEdit(final NodeLabel nodeLabel) {
		nodeLabel.setVisible(true);
	}

	/** {@inheritDoc} */
	@Override
	public void finishEdit(final NodeLabel nodeLabel, final String text) {
		eventBus.setLabel(new LabelEditCommand(this, //
				nodeLabel.getNodeId(), //
				text));
		nodeLabel.setVisible(true);
	}

	/** {@inheritDoc} */
	@Override
	public void addTextField(final NodeLabel nodeLabel, final CheckedTextField textField) {
		// Not nice that we grab parent, here!
		nodeLabel.getParent().add(textField);
		nodeLabel.getParent().repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void removeTextField(final NodeLabel nodeLabel, final CheckedTextField textField) {
		// Not nice that we grab parent, here!
		nodeLabel.getParent().remove(textField);
		nodeLabel.getParent().repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent e) {
		doCancelEdit();
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
	public void createPlace(final PlaceCreationCommand cmd) {
		doCancelEdit();
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		doCancelEdit();
	}

}
