package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

/**
 * <p>
 * RemoveSelectedNodesAction class.
 * </p>
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
	 * <p>
	 * newMenuItem.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param enabled
	 *            a boolean.
	 * @return a {@link javax.swing.JMenuItem} object.
	 */
	public static JMenuItem newMenuItem(final GuiEventBus eventBus, final Object source, final boolean enabled) {
		return new JMenuItem(new RemoveSelectedNodesAction(eventBus, source, enabled));
	}

	private final Object source;
	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * Constructor for RemoveSelectedNodesAction.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param enabled
	 *            a boolean.
	 */
	public RemoveSelectedNodesAction(final GuiEventBus eventBus, final Object source, final boolean enabled) {
		super(label);
		this.eventBus = eventBus;
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
