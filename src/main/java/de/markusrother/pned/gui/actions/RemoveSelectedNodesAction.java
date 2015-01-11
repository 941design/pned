package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.NodeSelectionListener;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;

/**
 * <p>
 * Action that triggers removal of selected nodes.
 * </p>
 * <p>
 * The selection state can be retrieved from
 * {@link de.markusrother.pned.gui.control.PnState}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class RemoveSelectedNodesAction extends AbstractStatefulAction
	implements
		NodeSelectionListener {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String name = "Remove selected nodes";
	/** Constant <code>mnemonic=KeyEvent.VK_R</code> */
	private static final int mnemonic = KeyEvent.VK_R;

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JMenuItem} where selection
	 * triggers removal of currently selected nodes.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the
	 *            current state.
	 * @return a {@link javax.swing.JMenuItem} - the created menu item.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	public static JMenuItem newMenuItem(final PnState state, final PnCommandTarget commandTarget) {
		return new JMenuItem(new RemoveSelectedNodesAction(state, commandTarget));
	}

	/**
	 * <p>
	 * Constructor for RemoveSelectedNodesAction.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} - the
	 *            current state.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	public RemoveSelectedNodesAction(final PnState state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, name);

		putValue(Action.MNEMONIC_KEY, mnemonic);
		setEnabled(state.areNodesSelected());

		// TODO - Currently, this is not registered as a listener to the event
		// bus. Instead actions are instantiated again when needed!
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO - iterate over selection and post one event each!
		commandTarget.removeSelectedNodes(new RemoveSelectedNodesEvent(this));
		setEnabled(false);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent event) {
		setEnabled(!event.getNodes().isEmpty());
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent event) {
		setEnabled(false);
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

}
