package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.core.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

/**
 * <p>RemoveSelectedNodesAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveSelectedNodesAction extends AbstractAction
	implements
		NodeSelectionListener {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove selected nodes";
	/** Constant <code>mnemonic=KeyEvent.VK_R</code> */
	private static final int mnemonic = KeyEvent.VK_R;

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param enabled a boolean.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final EventBus eventMulticaster, final Object source, final boolean enabled) {
		return new JMenuItem(new RemoveSelectedNodesAction(eventMulticaster, source, enabled));
	}

	private final Object source;
	private final EventBus eventBus;

	/**
	 * <p>Constructor for RemoveSelectedNodesAction.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param enabled a boolean.
	 */
	public RemoveSelectedNodesAction(final EventBus eventMulticaster, final Object source, final boolean enabled) {
		super(label);
		this.eventBus = eventMulticaster;
		this.source = source;
		putValue(Action.MNEMONIC_KEY, mnemonic);
		setEnabled(enabled);
		// FIXME - dispose!
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		eventBus.removeSelectedNodes(new RemoveSelectedNodesEvent(source));
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		setEnabled(!event.getNodes().isEmpty());
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		setEnabled(false);
	}

}
