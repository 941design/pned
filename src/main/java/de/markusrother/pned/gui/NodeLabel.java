package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.markusrother.pned.gui.style.NodeLabelStyle;
import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

public class NodeLabel extends JLabel implements NodeListener, Disposable {

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

	public enum State {
		DEFAULT, //
		HOVER, //
	}

	private final NodeMotionListener nodeMotionListener;
	private final DragDropListener dragDropListener;
	private final String nodeId;
	private State state;

	public NodeLabel(final String nodeId) {
		super(nodeId);
		this.nodeId = nodeId;
		// TODO - this needs to go to label, and be removed from eventBus upon
		// component removal!
		this.dragDropListener = new DefaultDragDropListener(this);
		DragDropListener.addToComponent(this, dragDropListener);
		HoverListener.addToComponent(this, new LabelHoverListener());
		this.nodeMotionListener = new NodeMotionListener() {

			@Override
			public void nodeMoved(final NodeMovedEvent event) {
				for (final AbstractNode node : event.getNodes()) {
					if (node.getId() == nodeId) {
						final Rectangle r = getBounds();
						r.translate(event.getDeltaX(), event.getDeltaY());
						setBounds(r);
						repaint();
					}
				}
			}

		};
		eventBus.addNodeListener(this);
		eventBus.addNodeMotionListener(nodeMotionListener);
		setState(State.DEFAULT);
	}

	public State getState() {
		return state;
	}

	public void setState(final State state) {
		// TODO - create interface Stateful
		this.state = state;
		style.apply(this);
		revalidate();
		repaint();
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		if (e.getNode().getId() == nodeId) {
			dispose();
		}
	}

	@Override
	public void dispose() {
		eventBus.removeNodeListener(this);
		eventBus.removeNodeMotionListener(nodeMotionListener);
		getParent().remove(this);
	}
}
