package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.listeners.LabelHoverListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.styles.NodeLabelStyle;
import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

public class NodeLabel extends JLabel
	implements
		NodeRemovalListener,
		Disposable,
		NodeMotionListener {

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

	public NodeLabel(final String nodeId) {
		super(nodeId);
		this.nodeId = nodeId;
		// TODO - this needs to go to label, and be removed from eventBus upon
		// component removal!
		this.dragDropListener = new DefaultDragDropListener<>(NodeLabel.class, this);
		DragDropListener.addToComponent(this, dragDropListener);
		HoverListener.addToComponent(this, new LabelHoverListener());
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		setState(ComponentState.DEFAULT);
	}

	public ComponentState getState() {
		return state;
	}

	public void setState(final ComponentState state) {
		// TODO - create interface Stateful
		this.state = state;
		style.apply(this);
		revalidate();
		repaint();
	}

	@Override
	public void nodeMoved(final NodeMovedEvent e) {
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

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		final String nodeId = e.getNode().getId();
		if (this.nodeId.equals(nodeId)) {
			dispose();
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	@Override
	public void dispose() {
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeMotionListener.class, this);
		getParent().remove(this);
	}
}
