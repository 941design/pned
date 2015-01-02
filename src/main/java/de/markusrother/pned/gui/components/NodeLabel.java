package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.LabelEditListener;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.LabelHoverListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.styles.NodeLabelStyle;
import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

/**
 * <p>NodeLabel class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabel extends JLabel
	implements
		LabelEditListener,
		NodeRemovalListener,
		Disposable,
		NodeMotionListener {

	/** Constant <code>style</code> */
	private static NodeLabelStyle style = new NodeLabelStyle();
	static {
		style.setDefaultFg(Color.BLACK);
		style.setDefaultBg(Color.WHITE);
		style.setDefaultBorder(new LineBorder(Color.GRAY));
		style.setDefaultOpacity(true);
		style.setHoverFg(Color.BLACK);
		style.setHoverBg(Color.LIGHT_GRAY);
		style.setHoverBorder(new LineBorder(Color.GREEN));
		style.setHoverOpacity(true);
	}

	private final DragDropListener<NodeLabel> dragDropListener;
	private final String nodeId;
	private ComponentState state;
	private final EventBus eventBus;

	/**
	 * <p>Constructor for NodeLabel.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param nodeId a {@link java.lang.String} object.
	 */
	public NodeLabel(final EventBus eventBus, final String nodeId) {
		this(eventBus, nodeId, nodeId);
	}

	/**
	 * <p>Constructor for NodeLabel.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param nodeId a {@link java.lang.String} object.
	 * @param nodeLabel a {@link java.lang.String} object.
	 */
	public NodeLabel(final EventBus eventBus, final String nodeId, final String nodeLabel) {
		super(nodeLabel);
		this.eventBus = eventBus;
		this.nodeId = nodeId;
		// TODO - this needs to go to label, and be removed from eventBus upon
		// component removal!
		this.dragDropListener = new DefaultDragDropListener<>(NodeLabel.class, this);
		DragDropListener.addToComponent(this, dragDropListener);
		HoverListener.addToComponent(this, new LabelHoverListener());
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(LabelEditListener.class, this); // FIXME - dispose!
		setState(ComponentState.DEFAULT);
	}

	/**
	 * <p>Getter for the field <code>state</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.components.ComponentState} object.
	 */
	public ComponentState getState() {
		return state;
	}

	/**
	 * <p>Setter for the field <code>state</code>.</p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.components.ComponentState} object.
	 */
	public void setState(final ComponentState state) {
		// TODO - create interface Stateful
		this.state = state;
		style.apply(this);
		revalidate();
		repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		for (final String nodeId : e.getNodeIds()) {
			if (this.nodeId.equals(nodeId)) {
				final Rectangle r = getBounds();
				r.translate(e.getDeltaX(), e.getDeltaY());
				setBounds(r);
				repaint();
				break;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		final String removedNodeId = e.getNodeId();
		if (this.nodeId.equals(removedNodeId)) {
			dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeMotionListener.class, this);
		getParent().remove(this);
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand e) {
		if (this.nodeId.equals(e.getElementId())) {
			setText(e.getLabel());
		}
	}
}
