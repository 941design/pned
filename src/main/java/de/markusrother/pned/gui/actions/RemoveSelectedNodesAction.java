package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.NodeSelectionListener;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.core.PnState;

/**
 * <p>
 * RemoveSelectedNodesAction class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveSelectedNodesAction extends AbstractStatefulAction
	implements
		NodeSelectionListener {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove selected nodes";
	/** Constant <code>mnemonic=KeyEvent.VK_R</code> */
	private static final int mnemonic = KeyEvent.VK_R;

	/**
	 * <p>
	 * newMenuItem.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} object.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final PnState state) {
		return new JMenuItem(new RemoveSelectedNodesAction(state));
	}

	/**
	 * <p>
	 * Constructor for RemoveSelectedNodesAction.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.core.PnState} object.
	 */
	public RemoveSelectedNodesAction(final PnState state) {
		super(label, state);

		putValue(Action.MNEMONIC_KEY, mnemonic);
		final boolean areNodesSelected = state.areNodesSelected();
		setEnabled(areNodesSelected);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		getEventBus().removeSelectedNodes(new RemoveSelectedNodesEvent(this));
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
